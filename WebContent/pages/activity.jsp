<%@page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css" href="../jquery/tabs.css"/>

<!--jquery-->	
<script src="../jquery/ui/i18n/ui.datepicker-pt-BR.js" type="text/javascript"></script>    
<script src="../jquery/jquery.impromptu.2.7.js" type="text/javascript"></script>
<script src="../jquery/ui/jquery-ui-1.7.2.custom.js" type="text/javascript"></script>
<script src="../jquery/ui/i18n/ui.datepicker-pt-BR.js" type="text/javascript"></script>    
<script src="../jquery/jquery.maskedinput-1.2.2.js" type="text/javascript"></script>
<script src="../jquery/jquery.impromptu.2.7.js" type="text/javascript"></script>
<script src="../_js/activity.js"></script>


<body>
<div id="formCadAtividade" title="">
	<form id="frmManterAtividade">
		<fieldset class="cadastros">
				<legend>Activity</legend>
				<div  class="rotulo">Name:</div>
				<input type=text id="txtNome" name="nomeAtividade" maxlength="50" style="width:500px"  value="${atividade.nomeAtividade}">
				<br>
				
				<div  class="rotulo">Program:</div>
				<input type=text id="txtPrograma" name="nomePrograma" maxlength="50" style="width:500px"  value="${atividade.nomePrograma}">
				<br>
				
				<div class="rotulo">Version:</div>
				<input type="text" id="txtVersao" name="versaoPrograma" maxlength="50" size="22"  value="${atividade.versaoPrograma}">
				<br>

				<div class="rotulo">Function:</div>
				<input type="text" id="txtFuncao" name="funcao" maxlength="50" style="width:500px"  value="${atividade.funcao}">
				<br>
				
				<div class="rotulo">Start Date/Hour:</div>
				<input type="text" id="txtInicioAtividade" name="dataInicio" maxlength="50" size="20"  value="<fmt:formatDate value="${atividade.dataHoraInicio}" pattern="dd/MM/yyyy"/>">
				<input type="text" id="txtHoraInicioAtividade" name="horaInicio" maxlength="50" size="10"  value="<fmt:formatDate value="${atividade.dataHoraInicio}" pattern="hh:mm"/>">
				<br>
				
				<div class="rotulo">End Date/Hour:</div>
				<input type="text" id="txtFimAtividade" name="dataFim" maxlength="50" size="20"  value="<fmt:formatDate value="${atividade.dataHoraFim}" pattern="dd/MM/yyyy"/>">
				<input type="text" id="txtHoraFimAtividade" name="horaFim" maxlength="50" size="10"  value="<fmt:formatDate value="${atividade.dataHoraFim}" pattern="hh:mm"/>">
				<br>
				
				<div class="rotulo">Command line:</div>
				<textarea id="txtComando" name="linhaComando" rows="5" maxlength="100" style="width: 500px">${atividade.linhaComando}</textarea>
				<br>
				
				<div class="rotulo">File:</div>
				<div id="nomeArquivoSelecionado"></div>
				<iframe src="upload.jsp" id="frameUpload" style="width:500px"></iframe>		
				<br>
				<input type="hidden" id="txtNomeArquivo" name="nomeArquivo"/>
				<input type="hidden" name="acao" value="salvar"/>
				
				<input type="hidden" id="idExperimento" name="idExperimento" value="${atividade.experimentoOrigem.id}"/>
				
				<input type="button" value="Run command" onclick="executarComando()">
				<input type="button" value="Clean" onclick="limparTela()">
				<c:choose>
					<c:when test="${not empty atividade.id && atividade.id gt 0}">
					    <input type="hidden" name="idAtividade" id="idAtividade" value="${atividade.id}"/>
						<input type="button" value="Clean" onclick="deletarAtividade()"/>			
					</c:when>
					<c:otherwise>
						<input type="hidden" name="idAtividade" id="idAtividade" value="0"/>		
					</c:otherwise>
				</c:choose>
				
				
				<input type="button" value="Save" onclick="salvarAtividade()">
		</fieldset>
	</form>

</div>
				

<div id="users-contain" class="ui-widget" style="display:none">
	<div id="telaResultadoComando">
		<textarea id="txtResultadoComando" style="width:450px; height: 380px" disabled></textarea>
	</div>
</div>

<div id="users-contain" class="ui-widget" style="display:none">
	<div id="divProcessando">
		<img src="../_img/loading_50_50.gif" id="imgProcessando" style="margin-left: 200px"/>
		<h1>Aguarde enquanto o comando est&aacute; sendo executado!!!</h1>
	</div>
</div>


</body>
</html>