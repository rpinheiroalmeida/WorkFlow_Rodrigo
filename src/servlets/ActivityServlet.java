package servlets;

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
import java.util.HashSet;
import java.util.List;

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

import com.thoughtworks.xstream.XStream;

import dao.BioInformaticaDaoIf;
import dao.impl.AtividadeDao;
import entidades.Atividade;
import entidades.Experimento;
import entidades.Projeto;

/**
 * Servlet implementation class ActivityServlet
 */
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String NOME_LISTA_ATIVIDADES = "listaAtividades";
	private static final String PATH_IMAGEM = "/";
	private static final String MENSAGEM_ERRO_UPLOAD = "[erro de upload]";
	private static final String NOME_OBJETO = "atividade";
	private Atividade atividade;

	private BioInformaticaDaoIf<Atividade> daoAtividade;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);		
	}
	
	@SuppressWarnings("unchecked")
	private void criarAtividadeDao(HttpServletRequest request) throws IOException {
		if (daoAtividade == null){
			request.getSession().setAttribute(NOME_LISTA_ATIVIDADES, new ArrayList<Projeto>());
	        daoAtividade = new AtividadeDao((List<Atividade>) request.getSession().getAttribute(NOME_LISTA_ATIVIDADES));
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
			atividade = buscarAtividade(atividade.getIdAtividade());
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
			if (! efetuarUpload(request).equalsIgnoreCase(MENSAGEM_ERRO_UPLOAD) ){
				retornarComandoAtualizacaoTela(response);	
			}
		}
	}

	private void atualizarObjetoNaSessao(HttpServletRequest request) {
		request.getSession().setAttribute(NOME_OBJETO, atividade);
		
	}

	private void retornarComandoAtualizacaoTela(HttpServletResponse response) throws IOException {
		response.getWriter().write("<script>alert(\"Upload efetuado com sucesso\");");
		response.getWriter().write("history.go(-1); </script>");
	}

	private void executarComando(String linhaComando, String nomeArquivo, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
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
	 * @throws FileUploadException
	 * @throws IOException
	 */
	private String efetuarUpload(HttpServletRequest request) throws IOException {
	   boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	   if (isMultipart) {
	       FileItemFactory factory = new DiskFileItemFactory();
	       ServletFileUpload upload = new ServletFileUpload(factory);
	       try {
	    	   FileItemIterator items = upload.getItemIterator(request);
	    	   FileItemStream item = null;
	    	   if (items.hasNext()) {
	    		  item = items.next();
	    	      gerarArquivoUploaded(item);
	    	   }
	    	   return (item == null)? MENSAGEM_ERRO_UPLOAD :item.getName();
	       }catch(Exception e){
	    	   return e.getMessage();
	       }
	   }
	   return MENSAGEM_ERRO_UPLOAD;
	}

	/**
	 * @param fileItemStream
	 * @throws FileUploadException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void gerarArquivoUploaded(FileItemStream item) throws FileUploadException, IOException, FileNotFoundException {
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
		  }
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
		if (atividade.getIdAtividade() != null && atividade.getIdAtividade() > 0){
			daoAtividade.alterar(atividade);
		} else {
			atividade.setIdAtividade(daoAtividade.listar().size()+1);			
			boolean ok = daoAtividade.incluir(atividade);
			if (!ok){
				retornarErro(writer);
				return;
			}
		}
		Experimento experimento = buscarExperimentoNaSessao(request, atividade.getExperimentoOrigem().getIdExperimento());
		if (experimento != null){
			if (experimento.getAtividades() == null ){
				experimento.setAtividades(new HashSet<Atividade>());
			}
			atividade.setExperimentoOrigem(experimento);
			experimento.getAtividades().add(atividade);
		}
		atualizarSessao(request);
		atualizarObjetoNaSessao(request);
		retornarSucesso(writer);
	}

	@SuppressWarnings("unchecked")
	private Experimento buscarExperimentoNaSessao(HttpServletRequest request, Integer idExperimento) {
		List<Experimento> experimentos = (List<Experimento>) request.getSession().getAttribute(ExperimentServlet.NOME_LISTA_EXPERIMENTOS);
		for(Experimento e : experimentos){
			if (e.getIdExperimento().equals(idExperimento)){
				return e;
			}
		}
		return null;
	}

	/**
	 * @param request
	 */
	private void atualizarSessao(HttpServletRequest request) {
		request.getSession().setAttribute(NOME_LISTA_ATIVIDADES, daoAtividade.listar());
	}

	private void retornarSucesso(PrintWriter writer) {
		writer.write("sucesso");
		
	}

	private void retornarErro(PrintWriter writer) {
		writer.write("erro");
	}


	private Atividade montarAtividade(HttpServletRequest request) {
		Atividade atividade = new Atividade();
		String idAtividade = request.getParameter("idAtividade");
		String idExperimento = request.getParameter("idExperimento");
		String dataHoraInicio = request.getParameter("dataInicio")+" "+request.getParameter("horaInicio");
		String dataHoraFim = request.getParameter("dataFim")+" "+request.getParameter("horaFim");

		atividade.setNomeAtividade(request.getParameter("nomeAtividade"));
		atividade.setNomePrograma(request.getParameter("nomePrograma"));
		atividade.setIdAtividade((idAtividade!=null && !idAtividade.equals(""))? Integer.valueOf(idAtividade): null);
		atividade.setExperimentoOrigem(new Experimento());
		atividade.getExperimentoOrigem().setIdExperimento((idExperimento != null && !idExperimento.isEmpty())? Integer.valueOf(idExperimento) : null);
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
			Integer idExperimento = objetoFiltro.getExperimentoOrigem()!= null? objetoFiltro.getExperimentoOrigem().getIdExperimento() : null;
			Integer idAtividade = objetoFiltro.getIdAtividade();
			
			if (idAtividade != null && idAtividade.equals(atividade.getIdAtividade())){
				listaRetorno.add(atividade);
				break;
			}
			
			if (idExperimento != null && atividade.getExperimentoOrigem().getIdExperimento().equals(idExperimento)){
				listaRetorno.add(atividade);
			}
			
		}
		
		//montar retorno da lista com xml
		response.getWriter().write(this.montarXml(listaRetorno));
	}
	
	private Atividade buscarAtividade(Integer idAtividade) throws IOException {
		for(Atividade atividade : daoAtividade.listar()){
			if (idAtividade.equals(atividade.getIdAtividade())){
				return atividade;
			}
		}
		return null;
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



