<%@page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="header.jsp" %>
<link rel="stylesheet" href="../_css/project.css"/>	
<link rel="stylesheet" type="text/css" href="../jquery/tabs.css"/>

<!--jquery-->	
<script src="../jquery/ui/jquery-ui-1.7.2.custom.js" type="text/javascript"></script>
<script src="../jquery/ui/i18n/ui.datepicker-pt-BR.js" type="text/javascript"></script>    
<script src="../jquery/jquery.maskedinput-1.2.2.js" type="text/javascript"></script>
<script src="../jquery/jquery.impromptu.2.7.js" type="text/javascript"></script>
<script src="../jquery/jquery/ui/ui.tabs.js" type="text/javascript"></script>    
<script src="../_js/project.js"></script>
	
<body>

<div id="formCadProjeto">
	
	<!--  <div id="carregando" style="display: inline; color: green;">Carregando...</div>  -->
	<div id="carregando" style="display: none;">
		<font color='#ffffff' size='3' style='font: italic;'>
			<b>
				<center>
					<h2><i><font color='#559900'>AGUARDE O CARREGAMENTO!...</font></i></h2>
					<br/>
					<img repet='y' src='http://vestibular2.bilac.com.br/PortalCandidato/Imagens/Carregando.gif'/>
					<div class='header-wrapper'></div>
				</center>
			</b>
		</font>
	</div>
	
	<form id="frmManter">
		<fieldset class="cadastros">
			<legend>Manuten&ccedil;&atilde;o de Projetos</legend>
			<div class="rotulo">Nome:</div>
			<input type="text" id="txtNome" name="nome" maxlength="50" size="100" value="${projeto.nome}"><br>				
			
			<div class="rotulo">Descri&ccedil;&atilde;o:</div><input type="text" id="txtDescricao" name="descricao" maxlength="50" size="100"  value="${projeto.descricao}"><br>
			<div class="rotulo">Coordenador:</div><input type="text" id="txtCoordenador" name="coordenador" maxlength="50" size="100"  value="${projeto.coordenador}"><br>
									
			<div class="rotulo">Data in&iacute;cio:</div><input type="text" id="txtInicio" name="dataHoraInicio" value="<fmt:formatDate value="${projeto.dataHoraInicio}" pattern="dd/MM/yyyy"/>"><br>				
		    <div class="rotulo">Data fim:</div><input type="text" id="txtFim" name="dataHoraFim"  value="<fmt:formatDate value="${projeto.dataHoraFim}" pattern="dd/MM/yyyy"/>"><br>
								
			<div class="rotulo">Observa&ccedil;&otilde;es:</div><textarea id="txtObservacao" name="observacao" rows="4" cols="76">${projeto.observacao}</textarea><br>
			
			<div id="abaAcesso">
				<div id="abaParticipante" class="abaAtiva" >Institui&ccedil;&otilde;es participantes</div>
				<div id="abaFinanciadora" class="abaInativa" >Institui&ccedil;&otilde;es financiadoras</div>
							
				<div id="inst_participantes"> 
					<input type="text" id="txtNomesParticipantes" size="90" maxlength="50">
					<img src="../_img/adiciona.png" id="imgAdicionaParticipante" name="imgAddParticipante" alt="Adicionar" onclick="adicionarNomeNaLista('txtNomesParticipantes','lstParticipantes')"/>
					<img src="../_img/cancel.png" id="imgRemoveParticipante" name="imgDelParticipante" alt="Remover" onclick="removerItem('lstParticipantes')"/>					
					<div>
						<select multiple class="lista_instituicoes" name="nomesInstituicoesParticipantes" id="lstParticipantes">
						<c:if test="${not empty projeto.nomesInstituicoesParticipantes}">
							<c:forEach var="participante" items="${projeto.nomesInstituicoesParticipantes}">
								<option value="${participante}">${participante}</option>
							</c:forEach>
						</c:if>
						</select>
					</div>
				</div>
	
				<div id="inst_financeiras">
					<input type="text" id="txtNomesFinanciadores" size="90" maxlength="50">
					<img src="../_img/adiciona.png" id="imgAdicionaFinanciadora" name="imgAddFinanciadora" alt="Adicionar" onclick="adicionarNomeNaLista('txtNomesFinanciadores','lstFinanciadoras')"/>
					<img src="../_img/cancel.png" id="imgRemoveFinanciadora" name="imgDelFinanciadora" alt="Remover" onclick="removerItem('lstFinanciadoras')"/>				
					
					<div>
						<select multiple class="lista_instituicoes" name="nomesInstituicoesFinanciadoras" id="lstFinanciadoras">
						<c:if test="${not empty projeto.nomesInstituicoesFinanciadoras}">
							<c:forEach var="financiadora" items="${projeto.nomesInstituicoesFinanciadoras}">
								<option value="${financiadora}">${financiadora}</option>
							</c:forEach>
						</c:if>
						
						</select>
					</div>
									
				</div>
			</div>
			<input type="hidden" name="acao" value="salvar"/>
			<input type="button" value="Limpar" onclick="limparTela()">
			<c:choose>
				<c:when test="${not empty projeto.id && projeto.id gt 0}">
					<input type="hidden" name="idProjeto" id="idProjeto" value="<c:out value="${projeto.id}"/>"/>
					<!--  <input type="button" id="excluir" value="Excluir" onclick="deletarProjeto()"> -->
					<input type="button" id="excluir" value="Excluir">			
				</c:when>
				<c:otherwise>
					<input type="hidden" name="idProjeto" id="idProjeto" value="0"/>
				</c:otherwise>
			</c:choose>
			<input type="button" value="Salvar" onclick="salvarProjeto()">

		</fieldset>
	</form>
	
</div>

</body>
</html>