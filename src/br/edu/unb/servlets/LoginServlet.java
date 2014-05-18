package br.edu.unb.servlets;

import java.io.IOException;
<<<<<<< HEAD:src/br/edu/unb/servlets/LoginServlet.java
import java.util.Arrays;
=======
import java.util.ArrayList;
>>>>>>> FETCH_HEAD:src/servlets/LoginServlet.java
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.unb.dao.impl.UsuarioDao;
import br.edu.unb.entities.Projeto;
import br.edu.unb.entities.Usuario;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
<<<<<<< HEAD:src/br/edu/unb/servlets/LoginServlet.java
//	private static final String NOME_USR_PADRAO = "Rodrigo Pinheiro";
=======
	private static final String NOME_USR_PADRAO = "Rodrigo Pinheiro";
>>>>>>> FETCH_HEAD:src/servlets/LoginServlet.java
	
	private static final String NOME_USUARIO_NA_SESSAO= "usuarioLogado";
	private static final String NOME_LISTA_USUARIOS = "listaUsuarios";
	protected static final String NOME_LISTA_PROJETOS = "listaProjetos";
	
	private UsuarioDao usuarioDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        usuarioDao = new UsuarioDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario usuario = montarUsuario(request);
		String acao =request.getParameter("acao");
		if (acao != null ){ 
			if (acao.equalsIgnoreCase("login")){
				tratarLoginUsuario(request, response, usuario);
			} else if (acao.equalsIgnoreCase("cadastro")){
				this.cadastrarUsuario(request, response, usuario);
			} else if (acao.equalsIgnoreCase("logoff")) {
				removerTodosAtributosDaSessao(request);
				request.getSession().invalidate();
				retornarResposta(true, response);
			}
		}
	}

	private void removerTodosAtributosDaSessao(HttpServletRequest request) {
		Enumeration<String> names = request.getSession().getAttributeNames();
		while (names.hasMoreElements()){
			request.getSession().removeAttribute(names.nextElement());
		}
	}

	private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws IOException {
//		List<Usuario> listaUsuariosCadastrados = this.listarUsuarios(usuario);
		Usuario pUsuario = recuperaUsuario(usuario);
		if (pUsuario != null){
			throw new IOException("Usuario ja cadastrado");
		} else {
//			listaUsuariosCadastrados.add(usuario);
			usuarioDao.createNodeUsuario(usuario);
			request.getSession().setAttribute(NOME_LISTA_USUARIOS, Arrays.asList(pUsuario));
			this.retornarResposta(true, response);	
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param usuario
	 * @throws IOException
	 */
	private void tratarLoginUsuario(HttpServletRequest request,HttpServletResponse response, Usuario usuario) throws IOException {
		Usuario pUsuario = recuperaUsuario(usuario);
		if (isUsuarioValido(pUsuario)){
//			usuario.setNome(NOME_USR_PADRAO);
			List<Projeto> projetos = usuarioDao.buscarProjetos(pUsuario);
			this.colocarUsuarioNaSessao(pUsuario, request);
			this.colocarListProjetosNaSessao(projetos, request);
			this.retornarResposta(true, response);
		} else {
			this.retornarResposta(false, response);
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private Usuario montarUsuario(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		usuario.setNome(request.getParameter("nome"));
		usuario.setLogin(request.getParameter("login"));
		usuario.setSenha(request.getParameter("senha"));
		return usuario;
	}
	
	private void retornarResposta(boolean b, HttpServletResponse response) throws IOException {
		response.getWriter().print((b)?"sucesso":"erro");
	}

	private void colocarUsuarioNaSessao(Usuario usuario, HttpServletRequest request) {
		HttpSession sessao = request.getSession();
		sessao.setAttribute(NOME_USUARIO_NA_SESSAO, usuario);
	}
	
	private void colocarListProjetosNaSessao(List<Projeto> projetos, HttpServletRequest request) {
		request.getSession().setAttribute(NOME_LISTA_PROJETOS, projetos);
	}

	private boolean isUsuarioValido(Usuario usuario){
//		Usuario pUsuario = recuperaUsuario(usuario);
		return usuario != null;
//		for(Usuario u : listaUsuarios){
//			if (u.getUsuario().equalsIgnoreCase(usuario.getUsuario()) && 
//				u.getSenha().equalsIgnoreCase(usuario.getSenha())	
//				){
//				return true;
//			}
//		}
//		return false;
	}
	
	private Usuario recuperaUsuario(Usuario usuario){
		return this.usuarioDao.recuperarUsuario(usuario);
		
//		List<Usuario> usuarios = new ArrayList<Usuario>();
//		HttpSession sessao = request.getSession();
//		if (sessao.getAttribute(NOME_LISTA_USUARIOS) != null){
//			usuarios = (List<Usuario>) sessao.getAttribute(NOME_LISTA_USUARIOS);
//		} else {
//			sessao.setAttribute(NOME_LISTA_USUARIOS, usuarios);
//		}
//		return usuarios;
	}

}
