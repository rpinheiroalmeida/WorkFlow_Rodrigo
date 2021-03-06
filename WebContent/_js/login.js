jQuery("document").ready(function(){
	
	$("document").ajaxStart($.blockUI).ajaxStop($.unblockUI);
		
	$("#frmCadastroUsuario").dialog({
    	autoOpen: false,
    	height: 250,
		width: 300,
    	modal: true,
    	buttons: {
    		'Fechar': function() {
    			$(this).dialog('close');
    		},
			'Salvar': function(){
					if (!dadosValidos()){
						$.prompt("Nome de usu&aacute;rio, login e senha devem ser informados!");
						return;
					}
				
    	        	$.ajax({
    	        	    url:'login.do?acao=cadastro',
    	        	    dataType:'html',
    	        	    resizable: false,
    	        	    data:$('#formCadastroUsuario').serialize(),
    	        	    type:'POST',
    	        	    success: function( data ){
    	        	    	if (data == "sucesso"){
    	        	    		$.prompt("Cadastro efetuado. O login j&aacute; est&aacute; liberado!");
    	        	    	} else {
    	        	    		$.prompt("Erro no cadastro");
    	        	    	}
    	        	    },	
    	        	    error: function( xhr, er ){
    	        	        $.prompt('Os dados n&atilde;o for&atilde;o salvos. Causa:' + data);
    	        	    }
    	        	});
    	        	$(this).dialog('close');
    	    	}				
			}
    });
	
});

function dadosValidos(){
	if ($("#nomeCadastro").val() == "" || $("#loginCadastro").val() == "" || $("#senhaCadastro").val() == ""){
		return false;
	}
	return true;
}

function autenticarUsuario(){
	
	if ($("#txtLogin").val() == "" || $("#txtSenha").val() == ""){
		$.prompt("Usu&aacute;rio e Senha devem ser informados!");
		return;
	}
	
	$.ajax({
		url:'login.do?acao=login',
		dataType:'html',
		data:$('#frmLogin').serialize(),
		type:'POST',
		success: function( data ){
			if (data == "sucesso"){
				window.location.href = 'pages/home.jsp';
			} else {
				$.prompt("Usu&aacute;rio inv&aacute;lido! Tente novamente.");
			}
		},
		error: function( xhr, er ){
			$.prompt('Os dados n&atilde;o for&atilde;o salvos. Motivo: ' + xhr.status + ', ' + xhr.statusText + ' | ' + er);
		}
	});
}

function abrirTelaCadastroUsuario(){
	
	$("#frmCadastroUsuario").dialog("open");
	$("#frmCadastroUsuario").dialog("option", "title", "Cadastramento de Usuarios");
}


function setaBotao(){
   key = window.event.keyCode;
   if (key == 13)
      document.forms[0].img_autenticar.focus();
}

