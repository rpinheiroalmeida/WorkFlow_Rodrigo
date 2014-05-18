package br.edu.unb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.unb.dao.BioInformaticaDaoIf;
import br.edu.unb.dao.impl.ExperimentoDao;
import br.edu.unb.entities.Experimento;
import br.edu.unb.entities.Projeto;

/**
 * Servlet implementation class ActivityServlet
 */
public class ExperimentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String NOME_LISTA_EXPERIMENTOS = "listaExperimentos";
	private static final String OBJETO_EDICAO = "experimento";
	private Experimento experimento;
	private BioInformaticaDaoIf<Experimento> daoExperimento;

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExperimentServlet() {
        super();
        daoExperimento = new ExperimentoDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);		
	}
	
	private void criarExperimentoDao(HttpServletRequest request) throws IOException {
		if (daoExperimento == null){
			request.getSession().setAttribute(NOME_LISTA_EXPERIMENTOS, new ArrayList<Projeto>());
//	        daoExperimento = new ExperimentoDaoImpl((List<Experimento>) request.getSession().getAttribute(NOME_LISTA_EXPERIMENTOS));
		}		
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		criarExperimentoDao(request);
		experimento = montarExperimento(request);
		
		String acao = request.getParameter("acao");
		
		if (acao.equalsIgnoreCase("novo")){
			atualizarSessao(request);
		} else if (acao.equalsIgnoreCase("salvar")){
			tratarSalvamentoDoObjeto(request, experimento, response.getWriter());
		} else if (acao.equalsIgnoreCase("consultar") ){
			experimento = buscarExperimento(request, experimento.getId());
			atualizarSessao(request);
		} else if (acao.equalsIgnoreCase("excluir")){
			tratarExclusaoProjeto(experimento, request);
		} 
		retornarSucesso(response.getWriter());
	}

	private void tratarExclusaoProjeto(Experimento experimento, HttpServletRequest request) {
		if (daoExperimento.excluir(experimento)){
			atualizarSessao(request);
		}
	}
	
	private void tratarSalvamentoDoObjeto(HttpServletRequest request, Experimento experimento, PrintWriter writer) {
		if (experimento.getId() != null && experimento.getId() > 0){
			daoExperimento.alterar(experimento);
		} else {
//			experimento.setIdExperimento(daoExperimento.listar().size()+1);			
			Experimento pExperimento = (Experimento) daoExperimento.incluir(experimento);
			experimento.setId(pExperimento.getId());
//			if (!ok){
//				retornarErro(writer);
//				return;
//			}
		}
		//adiciona o experimento lista dos experimentos do projeto
		Projeto projeto;
		try {
			projeto = this.buscarProjeto(Long.valueOf(experimento.getProjeto().getIdProjeto()), request);
			if (projeto.getExperimentos() == null){
				projeto.setExperimentos(new HashSet<Experimento>());
			}
			projeto.getExperimentos().add(experimento);
		} catch (IOException e) {
		}
		atualizarSessao(request);
//		retornarSucesso(writer);
	}

	/**
	 * @param request
	 */
	private void atualizarSessao(HttpServletRequest request) {
//		request.getSession().setAttribute(NOME_LISTA_EXPERIMENTOS, daoExperimento.listar());
		request.getSession().setAttribute(OBJETO_EDICAO, experimento);
	}

	private void retornarSucesso(PrintWriter writer) {
		writer.write("sucesso");
		
	}

//	private void retornarErro(PrintWriter writer) {
//		writer.write("erro");
//	}


	private Experimento montarExperimento(HttpServletRequest request) {
		
		System.out.println("Id experimento:" + request.getParameter("idProjeto"));
		
		experimento = new Experimento();
		String idExperimento = request.getParameter("idExperimento");
		String idProjeto = request.getParameter("idProjeto");
		String dataInicio = request.getParameter("dataInicio");
		String dataVersao = request.getParameter("dataVersao");
		String dataFim = request.getParameter("dataFim");

		if (idExperimento != null){
			experimento.setId(Long.valueOf(idExperimento));
		}
		experimento.setAnotacoes(request.getParameter("anotacoes"));
		experimento.setDescricao(request.getParameter("descricao"));
		experimento.setLocalExecucao(request.getParameter("localExecucao"));
		experimento.setNome(request.getParameter("nomeExperimento"));
		experimento.setProjeto(new Projeto());
		if (idProjeto != null && !idProjeto.isEmpty() ){
			experimento.getProjeto().setIdProjeto(Long.valueOf(idProjeto));
		} else {
			experimento.getProjeto().setIdProjeto(Long.valueOf(0));
		}
		
		experimento.setVersao(request.getParameter("versao"));
		
		try {
			if (dataInicio != null && !dataInicio.isEmpty()){
				experimento.setDataHoraInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio));
			}
			if (dataFim != null && !dataFim.isEmpty()){
				experimento.setDataHoraFim(new SimpleDateFormat("dd/MM/yyyy").parse(dataFim));	
			}
			
			if (dataVersao != null && !dataVersao.isEmpty()){
				experimento.setDataVersao(new SimpleDateFormat("dd/MM/yyyy").parse(dataVersao));	
			}
			
		} catch (ParseException e) {
			experimento.setDataHoraInicio(null);
			experimento.setDataHoraFim(null);
			experimento.setDataVersao(null);			
		}	
		
		return experimento;
	
	}


	private Experimento buscarExperimento(HttpServletRequest request, Long idExperimento) throws IOException {
		@SuppressWarnings("unchecked")
		List<Projeto> projetos = (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
		if (projetos != null && !projetos.isEmpty()) {
			for (Projeto projeto : projetos) {
				Set<Experimento> experimentos = projeto.getExperimentos();
				if (experimentos != null && !experimentos.isEmpty()) {
					for (Experimento experimento : experimentos) {
						if (idExperimento.equals(experimento.getId())) {
							return experimento;
						}
					}
				}
			}
		}
		return daoExperimento.recuperar(idExperimento);
	}


	@SuppressWarnings("unchecked")
	private Projeto buscarProjeto(Long idProjeto, HttpServletRequest request) throws IOException {
		List<Projeto> projetos = (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
		if (projetos != null && projetos.size() >0){

			for(Projeto p : projetos){
				if (idProjeto.equals(p.getIdProjeto())){
					return p;
				}
			}
		}
		return null;
	}	

}



