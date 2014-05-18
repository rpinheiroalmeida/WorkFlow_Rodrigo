package br.edu.unb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
import br.edu.unb.dao.BioInformaticaDaoIf;
import br.edu.unb.dao.impl.ProjetoDao;
import br.edu.unb.entities.Projeto;
import br.edu.unb.entities.Usuario;
=======
import dao.BioInformaticaDaoIf;
import dao.impl.ProjetoDao;
import entidades.Atividade;
import entidades.Experimento;
import entidades.Projeto;
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java

/**
 * Servlet implementation class ProjectServlet
 */
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected static final String NOME_LISTA_PROJETOS = "listaProjetos";
	private static final String PROJETO_EDICAO = "projeto";
<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
	private static final String NOME_USUARIO_NA_SESSAO= "usuarioLogado";
=======
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java
	private BioInformaticaDaoIf<Projeto> daoProjeto;
	private Projeto projetoEdicao = new Projeto();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
        daoProjeto = new ProjetoDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);		
	}
	
	private void criarProjetoDao(HttpServletRequest request) throws IOException {
		if (daoProjeto == null){
			request.getSession().setAttribute(NOME_LISTA_PROJETOS, new ArrayList<Projeto>());
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		criarProjetoDao(request);
		Projeto projeto = montarProjeto(request);
		
		String acao = request.getParameter("acao");
		
		if (acao.equalsIgnoreCase("novo")){
			projetoEdicao = new Projeto();
			atualizarRequest(request);
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("salvar")){
			tratarSalvamentoDoProjeto(request, projeto, response.getWriter());
		} else if (acao.equalsIgnoreCase("consultar") ){
<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
			projetoEdicao= buscarProjeto(request, projeto.getIdProjeto());
=======
			projetoEdicao= buscarProjeto(projeto.getIdProjeto());
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java
			atualizarRequest(request);
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("excluir")){
			tratarExclusaoProjeto(projeto, request);
			retornarSucesso(response.getWriter());
		} else if (acao.equalsIgnoreCase("listar") ){
			//response.getWriter().write(this.montarXml(daoProjeto.listar()));
<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
			List<Projeto> projetos = daoProjeto.listar();
			atualizarSessao(request, projetos);
=======
			atualizarSessao(request);
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java
		}
	}

	private void atualizarRequest(HttpServletRequest request) {
		request.getSession().setAttribute(PROJETO_EDICAO, projetoEdicao);
	}

	private void tratarExclusaoProjeto(Projeto projeto, HttpServletRequest request) {
		if (daoProjeto.excluir(projeto)){
//			atualizarSessao(request);
		}
	}
	
	private void tratarSalvamentoDoProjeto(HttpServletRequest request, Projeto projeto, PrintWriter writer) {
		if (projeto.getIdProjeto() != null && projeto.getIdProjeto() > 0){
			daoProjeto.alterar(projeto);
		} else {
			Projeto pProjeto = (Projeto) daoProjeto.incluir(projeto);
			projeto.setId(pProjeto.getId());
		}
<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
		List<Projeto> projetos = obterProjetosSessao(request);
		projetos.add(projeto);
		atualizarRequest(request);		
		atualizarSessao(request, projetos);
=======
		atualizarRequest(request);		
		atualizarSessao(request);
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java
		retornarSucesso(writer);
	}

	@SuppressWarnings("unchecked")
	private List<Projeto> obterProjetosSessao(HttpServletRequest request) {
		return (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
	}

	/**
	 * @param request
	 */
	private void atualizarSessao(HttpServletRequest request, List<Projeto> projetos) {
		request.getSession().setAttribute(NOME_LISTA_PROJETOS, projetos);
	}

	private void retornarSucesso(PrintWriter writer) {
		writer.write("sucesso");
		
	}

	private void retornarErro(PrintWriter writer) {
		writer.write("erro");
	}


	private Projeto montarProjeto(HttpServletRequest request) {
		Projeto projeto = new Projeto();
		String idProjeto = request.getParameter("idProjeto");
		String dataInicio = request.getParameter("dataHoraInicio");
		String dataFim = request.getParameter("dataHoraFim");
		Usuario usuario = (Usuario) request.getSession().getAttribute(NOME_USUARIO_NA_SESSAO);
		
		projeto.setUsuario(usuario);
		projeto.setIdProjeto((idProjeto != null && !idProjeto.equals("0"))? Long.valueOf(idProjeto) : null);
		projeto.setNome(request.getParameter("nome"));
		
		projeto.setCoordenador(request.getParameter("coordenador"));
		projeto.setDescricao(request.getParameter("descricao"));
		
		try {
			if (dataInicio != null && !dataInicio.isEmpty()){
				projeto.setDataHoraInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicio));
			}
			if (dataFim != null && !dataFim.isEmpty()){
				projeto.setDataHoraFim(new SimpleDateFormat("dd/MM/yyyy").parse(dataFim));	
			}
		} catch (ParseException e) {
			projeto.setDataHoraInicio(null);
			projeto.setDataHoraFim(null);
		}	
		projeto.setObservacao(request.getParameter("observacao"));
		projeto.setNomesInstituicoesParticipantes(montarSetInstituicoes(request.getParameterValues("nomesInstituicoesParticipantes")));
		projeto.setNomesInstituicoesFinanciadoras(montarSetInstituicoes(request.getParameterValues("nomesInstituicoesFinanciadoras")));
		return projeto;
	}
	
	private Set<String> montarSetInstituicoes(String[] parameterValues) {
		if (parameterValues != null){
			Set<String> conjunto = new HashSet<String>();
			if (parameterValues.length > 0){
				conjunto.addAll(Arrays.asList(parameterValues));
			}
			return conjunto;
		} else {
			return null;
		}
	}

<<<<<<< HEAD:src/br/edu/unb/servlets/ProjectServlet.java
	private Projeto buscarProjeto(HttpServletRequest request, Long idProjeto) throws IOException {
		@SuppressWarnings("unchecked")
		List<Projeto> projetos = (List<Projeto>) request.getSession().getAttribute(ProjectServlet.NOME_LISTA_PROJETOS);
		if (projetos != null && !projetos.isEmpty()) {
			for (Projeto projeto : projetos) {
				if (idProjeto.equals(projeto.getIdProjeto())) {
					return projeto;
				}
			}
		}
		return daoProjeto.recuperar(idProjeto);
=======
	private Projeto buscarProjeto(Integer idProjeto) throws IOException {
		for(Projeto projeto : daoProjeto.listar()){
			if (idProjeto.equals(projeto.getIdProjeto())){
				return projeto;
			}
		}
		return null;
>>>>>>> FETCH_HEAD:src/servlets/ProjectServlet.java
	}

/*	private String montarXml(List<Projeto> listaProjetos) {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		XStream x = new XStream();
		x.alias("projetos", List.class);
		x.alias("projeto", Projeto.class);
		builder.append(x.toXML(listaProjetos));
		
		System.out.println(builder.toString());
		
		return builder.toString();
	}*/
}
