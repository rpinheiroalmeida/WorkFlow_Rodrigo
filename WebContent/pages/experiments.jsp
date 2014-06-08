<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="../jquery/tabs.css"/>

<!--jquery-->	
<script src="../jquery/ui/i18n/ui.datepicker-pt-BR.js" type="text/javascript"></script>    
<script src="../jquery/jquery.impromptu.2.7.js" type="text/javascript"></script>
<script src="../jquery/ui/jquery-ui-1.7.2.custom.js" type="text/javascript"></script>
<script src="../jquery/ui/i18n/ui.datepicker-pt-BR.js" type="text/javascript"></script>    
<script src="../jquery/jquery.maskedinput-1.2.2.js" type="text/javascript"></script>
<script src="../jquery/jquery.impromptu.2.7.js" type="text/javascript"></script>
<script src="../_js/experiment.js"></script>
    	
<body>

<div id="formCadExperimento">
	<form id="frmManter">
		<fieldset class="cadastros">
			<legend>Manuten&ccedil;&atilde;o de Experimentos</legend>
				<div class="rotulo">Nome:</div>
				<input type="text" id="txtNome" name="nomeExperimento" maxlength="50" size="100" value="${experimento.nome}"><br>				
				
				<div class="rotulo">Descri&ccedil;&atilde;o:</div><input type="text" id="txtDescricao" name="descricao" maxlength="50" size="100" value="${experimento.descricao}"><br>
				<div class="rotulo">Local:</div><input type="text" id="txtLocal" name="localExecucao" maxlength="50" size="100" value="${experimento.localExecucao}"><br>
				<div class="rotulo">Vers&atilde;o:</div><input type="text" id="txtVersao" name="versao" maxlength="50" size="10" value="${experimento.versao}"><br>
										
				<div class="rotulo">Data in&iacute;cio:</div><input type="text" id="txtDataInicio" name="dataInicio" value="<fmt:formatDate value="${experimento.dataHoraInicio}" pattern="dd/MM/yyyy"/>"><br>				
			    <div class="rotulo">Data fim:</div><input type="text" id="txtDataFim" name="dataFim" value="<fmt:formatDate value="${experimento.dataHoraFim}" pattern="dd/MM/yyyy"/>"><br>
			    <div class="rotulo">Data vers&atilde;o:</div><input type="text" id="txtDataVersao" name="dataVersao" value="<fmt:formatDate value="${experimento.dataVersao}" pattern="dd/MM/yyyy"/>"><br>
									
				<div class="rotulo">Anota&ccedil;&otilde;es:</div><textarea id="txtObservacao" name="anotacoes" rows="4" cols="76">${experimento.anotacoes}</textarea><br>			
			
				<input type="hidden" name="acao" value="salvar"/>
				<input type="hidden" name="idProjeto" id="idProjeto" value="<c:out value="${experimento.projeto.id}"/>"/>
						
				<input type="button" value="Limpar" onclick="limparTela()">
				<c:choose>
					<c:when test="${not empty experimento.id && experimento.id gt 0}">
						<input type="hidden" name="idExperimento" id="idExperimento" value="${experimento.id}"/>
						<input type="button" value="Excluir" onclick="deletarExperimento()">			
					</c:when>
					<c:otherwise>
						<input type="hidden" name="idExperimento" id="idExperimento" value="0"/>		
					</c:otherwise>
				</c:choose>
				<input type="button" value="Salvar" onclick="salvarExperimento()">						
		</fieldset>	
	</form>	
</div>

</body>
</html>