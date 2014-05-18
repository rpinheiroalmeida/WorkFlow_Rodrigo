package br.edu.unb.servlets;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import br.edu.unb.dao.BioInformaticaDaoIf;
import br.edu.unb.dao.impl.AtividadeDao;
import br.edu.unb.dao.impl.CollectionProvenanceDao;
import br.edu.unb.entities.Atividade;
import br.edu.unb.entities.CollectionProvenance;
import br.edu.unb.entities.Experimento;
import br.edu.unb.entities.Projeto;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.entities.Usuario;

import com.thoughtworks.xstream.XStream;


/**
 * Servlet implementation class ActivityServlet
 */
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NOME_LISTA_ATIVIDADES = "listaAtividades";
	private static final String PATH_IMAGEM = "/";
//	private static final String MENSAGEM_ERRO_UPLOAD = "[erro de upload]";
	private static final String NOME_OBJETO = "atividade";
	private static final String NOME_COLLECTION_PROVENANCE = "collectionProvenance";
	private static final String NOME_USUARIO_NA_SESSAO= "usuarioLogado";
	private Atividade atividade;

	private BioInformaticaDaoIf<Atividade> daoAtividade;
	private BioInformaticaDaoIf<CollectionProvenance> daoCollection;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActivityServlet() {
		super();
		daoAtividade = new AtividadeDao();
		daoCollection = new CollectionProvenanceDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);		
	}

	private void criarAtividadeDao(HttpServletRequest request) throws IOException {
		if (daoAtividade == null){
			request.getSession().setAttribute(NOME_LISTA_ATIVIDADES, new ArrayList<Projeto>());
			//	        daoAtividade = new AtividadeDao((List<Atividade>) request.getSession().getAttribute(NOME_LISTA_ATIVIDADES));
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		criarAtividadeDao(request);
		atividade = montarAtividade(request);

		String acao = request.getParameter("acao");

		if (acao.equalsIgnoreCase("novo")){
			atualizarObjetoNaSessao(request);
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("salvar")){
			tratarSalvamentoDoObjeto(request, atividade, response.getWriter());
		} else if (acao.equalsIgnoreCase("consultar") ){
			atividade = buscarAtividade(request, atividade.getId());
			atualizarObjetoNaSessao(request);
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("excluir")){
			tratarExclusaoProjeto(atividade, request);
			atualizarObjetoNaSessao(request);			
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("executar")){
			executarComando(request.getParameter("linhaComando"), request.getParameter("nomeArquivo"), response);
		} else if (acao.equalsIgnoreCase("listar") ){
			this.listar(atividade, response);
		} else if (acao.equalsIgnoreCase("upload")){
			CollectionProvenance collection = null;
			try {
				collection = efetuarUpload(request);
			} catch (Exception e) {
				throw new ServletException(e);
			}
			retornarComandoAtualizacaoTela(response);	
			if (collection != null) {
				atualizarCollectionNaSessao(request, collection);
			}
		}
	}

	private void atualizarObjetoNaSessao(HttpServletRequest request) {
		request.getSession().setAttribute(NOME_OBJETO, atividade);
	}
	
	private void atualizarCollectionNaSessao(HttpServletRequest request, CollectionProvenance collection) {
		request.getSession().setAttribute(NOME_COLLECTION_PROVENANCE, collection);
	}
	
	private void retornarComandoAtualizacaoTela(HttpServletResponse response) throws IOException {
		response.getWriter().write("<script>alert(\"Upload efetuado com sucesso\");");
		response.getWriter().write("history.go(-1); </script>");
	}

	private void executarComando(String linhaComando, String nomeArquivo, HttpServletResponse response) throws IOException {
		if (linhaComando == null || linhaComando.length() == 0){
			response.getWriter().write("Linha de comando deve ser cadastrada para executar essa atividade!");
			return;
		}
		//new ExecucaoComandoAtividadeThread(linhaComando, response.getWriter()).start();
		//TODO: adaptar para sua realidade...
		//String comando = linhaComando + nomeArquivo; 
		executar(linhaComando, response.getWriter());
	}

	/**
	 * @param request
	 * @throws Exception 
	 * @throws FileUploadException
	 * @throws IOException
	 */
	private CollectionProvenance efetuarUpload(HttpServletRequest request) throws Exception  {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				FileItemIterator items = upload.getItemIterator(request);
				FileItemStream item = null;
				if (items.hasNext()) {
					item = items.next();
					return gerarArquivoUploaded(item);
				}
			}catch(Exception e){
				throw e;
			}
		}
		return null;
	}

	/**
	 * @param fileItemStream
	 * @throws FileUploadException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private CollectionProvenance gerarArquivoUploaded(FileItemStream item) throws FileUploadException, IOException, FileNotFoundException {
		CollectionProvenance collection = new CollectionProvenance();
		if (!item.isFormField()) {
			String fileName = item.getName();
			String root = getServletContext().getRealPath(PATH_IMAGEM);
			File uploadedFile = new File(root + "/" + fileName);
			InputStream is = item.openStream();
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(uploadedFile));
			int dado = -1;
			while((dado = is.read()) != -1){
				output.write(dado);
			}
			output.close();
			is.close();
			
			collection.setLocation(uploadedFile.getAbsolutePath());
			collection.setName(fileName);
			collection.setSize(uploadedFile.length());
		}
		return collection;
	}

	private void executar(String linhaComando, Writer consoleSaida){
		try {
			Process processo = Runtime.getRuntime().exec(linhaComando);
			InputStream is = processo.getInputStream();
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(isreader);			
			String linha = "";
			while ((linha = input.readLine()) != null) {
				consoleSaida.write(linha);
				consoleSaida.write("\n");
			}			
		} catch (IOException e) {
			try {
				consoleSaida.write("Erro:" + e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
	}

	private void tratarExclusaoProjeto(Atividade atividade, HttpServletRequest request) {
		if (daoAtividade.excluir(atividade)){
			atualizarSessao(request);
		}
	}

	private void tratarSalvamentoDoObjeto(HttpServletRequest request, Atividade atividade, PrintWriter writer) {
		if (atividade.getId() != null && atividade.getId() > 0){
			Object object = obterObjetoSessao(request, NOME_COLLECTION_PROVENANCE);
			if (object != null) {
				atividade.setCollection((CollectionProvenance) object);
			}
			daoAtividade.alterar(atividade);
		} else {
			Object object = obterObjetoSessao(request, NOME_COLLECTION_PROVENANCE);
			Atividade pAtividade = (Atividade) daoAtividade.incluir(atividade);
			atividade.setId(pAtividade.getId());
			atividade.setCollection(pAtividade.getCollection());
			if (object != null) {
				CollectionProvenance collection = (CollectionProvenance) daoCollection.incluir((CollectionProvenance) object);
				daoAtividade.createRelationship(atividade, collection, RelationshipProvenanceType.USED);
			}
			Usuario usuario = (Usuario) obterObjetoSessao(request, NOME_USUARIO_NA_SESSAO);
			daoAtividade.createRelationship(usuario, atividade, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
		}
		Experimento experimento;
		try {
			experimento = buscarExperimento(atividade.getExperimentoOrigem().getId(), request);
			if (experimento != null) {
				experimento.getAtividades().add(atividade);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		atualizarSessao(request);
		atualizarObjetoNaSessao(request);
		retornarSucesso(writer);
	}

	private Object obterObjetoSessao(HttpServletRequest request, String nomeCollectionProvenance) {
		return request.getSession().getAttribute(nomeCollectionProvenance);
	}
	
	@SuppressWarnings("unchecked")
	private Experimento buscarExperimento(Long idExperimento, HttpServletRequest request) throws Exception {
		List<Projeto> projetos = (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
		if (projetos != null && projetos.size() > 0){

			for(Projeto p : projetos){
				Set<Experimento> experimentos = p.getExperimentos();
				if (experimentos != null) {
					for (Experimento experimento : experimentos) {
						if (idExperimento.equals(experimento.getId())) 
							return experimento;
					}
				}
			}
		}
		return null;
	}	


//	@SuppressWarnings("unchecked")
//	private Experimento buscarExperimentoNaSessao(HttpServletRequest request, Long idExperimento) {
//		List<Experimento> experimentos = (List<Experimento>) request.getSession().getAttribute(ExperimentServlet.NOME_LISTA_EXPERIMENTOS);
//		for(Experimento e : experimentos){
//			if (e.getId().equals(idExperimento)){
//				return e;
//			}
//		}
//		return null;
//	}

	/**
	 * @param request
	 */
	private void atualizarSessao(HttpServletRequest request) {
		request.getSession().setAttribute(NOME_LISTA_ATIVIDADES, daoAtividade.listar());
	}

	private void retornarSucesso(PrintWriter writer) {
		writer.write("sucesso");

	}

//	private void retornarErro(PrintWriter writer) {
//		writer.write("erro");
//	}


	private Atividade montarAtividade(HttpServletRequest request) {
		Atividade atividade = new Atividade();
		String idAtividade = request.getParameter("idAtividade");
		String idExperimento = request.getParameter("idExperimento");
		String dataHoraInicio = request.getParameter("dataInicio")+" "+request.getParameter("horaInicio");
		String dataHoraFim = request.getParameter("dataFim")+" "+request.getParameter("horaFim");

		atividade.setNomeAtividade(request.getParameter("nomeAtividade"));
		atividade.setNomePrograma(request.getParameter("nomePrograma"));
		atividade.setId((idAtividade!=null && !idAtividade.equals(""))? Long.valueOf(idAtividade): null);
		atividade.setExperimentoOrigem(new Experimento());
		atividade.getExperimentoOrigem().setId((idExperimento != null && !idExperimento.isEmpty())? Long.valueOf(idExperimento) : null);
		atividade.setNomePrograma(request.getParameter("nomePrograma"));

		atividade.setVersaoPrograma(request.getParameter("versaoPrograma"));
		atividade.setFuncao(request.getParameter("funcao"));
		atividade.setNomeArquivo(request.getParameter("nomeArquivo"));

		try {
			if (dataHoraInicio != null && !dataHoraInicio.isEmpty()){
				atividade.setDataHoraInicio(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataHoraInicio));
			}
			if (dataHoraFim != null && !dataHoraFim.isEmpty()){
				atividade.setDataHoraFim(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataHoraFim));	
			}
		} catch (ParseException e) {
			atividade.setDataHoraInicio(null);
			atividade.setDataHoraFim(null);
		}	
		atividade.setLinhaComando(request.getParameter("linhaComando"));

		return atividade;
	}


	private void listar(Atividade objetoFiltro, HttpServletResponse response) throws IOException {
		List<Atividade> listaRetorno = new ArrayList<Atividade>();
		for(Atividade atividade : daoAtividade.listar()){
			Long idExperimento = objetoFiltro.getExperimentoOrigem()!= null? objetoFiltro.getExperimentoOrigem().getId() : null;
			Long idAtividade = objetoFiltro.getId();

			if (idAtividade != null && idAtividade.equals(atividade.getId())){
				listaRetorno.add(atividade);
				break;
			}

			if (idExperimento != null && atividade.getExperimentoOrigem().getId().equals(idExperimento)){
				listaRetorno.add(atividade);
			}

		}

		//montar retorno da lista com xml
		response.getWriter().write(this.montarXml(listaRetorno));
	}

	private Atividade buscarAtividade(HttpServletRequest request, Long idAtividade) throws IOException {
		@SuppressWarnings("unchecked")
		List<Projeto> projetos = (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
		if (projetos != null && !projetos.isEmpty()) {
			for (Projeto projeto : projetos) {
				Set<Experimento> experimentos = projeto.getExperimentos();
				if (experimentos != null && !experimentos.isEmpty()) {
					for (Experimento experimento : experimentos) {
						Set<Atividade> atividades = experimento.getAtividades();
						for (Atividade atividade : atividades) {
							if (idAtividade.equals(atividade.getId())) {
								return atividade;
							}

						}
					}
				}
			}
		}

		return daoAtividade.recuperar(idAtividade);
		//		for(Atividade atividade : daoAtividade.listar()){
		//			if (idAtividade.equals(atividade.getIdAtividade())){
		//				return atividade;
		//			}
		//		}
		//		return null;
	}	

	private String montarXml(List<Atividade> listaAtividade) {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		XStream x = new XStream();
		x.alias(NOME_LISTA_ATIVIDADES, List.class);
		x.alias("atividade", Atividade.class);
		builder.append(x.toXML(listaAtividade));



		return builder.toString();
	}

}



