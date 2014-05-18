$(document).ready(function(){

	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$("#projetos").treeview();
	
<<<<<<< HEAD
});
=======
	
}); 
>>>>>>> FETCH_HEAD

function abrirTela(nome, id){
	var nomeServlet = "";
	var paramsServlet = new Object();
	
	if (nome == "projeto"){
		nomeServlet = "../project.do";
		paramsServlet = "idProjeto="+id;
		nome = "project.jsp";
	} else if (nome == "experimento"){
		nomeServlet = "../experiment.do";
		paramsServlet = "idExperimento="+id;
		nome = "experiments.jsp";
	} else if (nome == "atividade"){
		nomeServlet = "../activity.do";
		paramsServlet = "idAtividade="+id;
		nome = "activity.jsp";
	} else {
<<<<<<< HEAD
		$.prompt("PÃ¡gina invÃ¡lida!");
=======
		$.prompt("Página inválida!");
>>>>>>> FETCH_HEAD
		return;
	}
	
	nomeServlet += "?acao=consultar&"+paramsServlet; 
	enviar(nomeServlet, nome, true, "exibicao_conteudo");
}

function efetuarLogoff(){
	enviar('../login.do?acao=logoff', '../index.jsp', false, "");
}

function novoProjeto(){
	enviar("../project.do?acao=novo", "project.jsp", true, "exibicao_conteudo");
	fecharTodosMenus();
}

function novoExperimento(idProjeto){
	enviar("../experiment.do?acao=novo&idProjeto="+idProjeto, "experiments.jsp", true, "exibicao_conteudo");
	fecharTodosMenus();
}

function novaAtividade(idExperimento){
	enviar("../activity.do?acao=novo&idExperimento="+idExperimento, "activity.jsp", true, "exibicao_conteudo");
	fecharTodosMenus();
}


function enviar(pUrl, pNomeTelaAbrir, pAbreNoFrame, pIdTela ){
	$.ajax({
		url:pUrl,
		dataType:'html',
		type:'POST',
		success: function( data ){
			if (data == "sucesso"){
				if (pAbreNoFrame){
					$("#"+pIdTela).load(pNomeTelaAbrir);
				} else {
					window.location.href= pNomeTelaAbrir;
				}
			} else {
				$.prompt("Erro ao efetuar acao para a tela:" + pNomeTelaAbrir);
			}
		},
		error: function( xhr, er ){
			$.prompt('Os dados n&atilde;o for&atilde;o enviados. Motivo: ' + xhr.status + ', ' + xhr.statusText + ' | ' + er);
		}
	});	
	
}

function fecharTodosMenus(){
	$(".menu_contexto").each(function(){
		$(this).hide('normal');
	});	
}

function mostrarContexto(pElemOrigem, pIdMenu){
<<<<<<< HEAD
	//pegar a posiÃ§Ã£o do clique
=======
	//pegar a posição do clique
>>>>>>> FETCH_HEAD
	fecharTodosMenus();
	var menu = document.getElementById(pIdMenu);
	var posicaoElemento = getPosicaoElemento(pElemOrigem.id);
	var largura = (pElemOrigem.width != null)? pElemOrigem.width : 50;
	
	var left = posicaoElemento.left + (largura/2);
	var top = posicaoElemento.top + (pElemOrigem.height/2);
	
	menu.style.visibility = "visible";
	menu.style.display = "block";
	menu.style.position="absolute";
	menu.style.top = top + "px";
	menu.style.left= left + "px";
	menu.style.border="1px solid #000000";
	menu.style.background="#ffffff";
	menu.style.boxShadow= "5px 5px 5px rgba(0,0,0, 0.4)";
}	

function apagarContexto(pIdMenu){
	$("#"+pIdMenu).hide('normal');
}

function getPosicaoElemento(elemID){
    var offsetTrail = document.getElementById(elemID);
    var offsetLeft = 0;
    var offsetTop = 0;
    while (offsetTrail) {
        offsetLeft += offsetTrail.offsetLeft;
        offsetTop += offsetTrail.offsetTop;
        offsetTrail = offsetTrail.offsetParent;
    }
    if (navigator.userAgent.indexOf("Mac") != -1 && 
        typeof document.body.leftMargin != "undefined") {
        offsetLeft += document.body.leftMargin;
        offsetTop += document.body.topMargin;
    }
    return {left:offsetLeft, top:offsetTop};
}
