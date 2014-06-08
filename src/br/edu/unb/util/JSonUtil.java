package br.edu.unb.util;

import java.util.Set;

import br.edu.unb.entities.Atividade;

public class JSonUtil {

	public static String buildJson(Set<Atividade> atividades) {
		StringBuilder builder = new StringBuilder();
		for (Atividade atividade : atividades) {
			builder.append("{")
					   .append("id : ").append(atividade.getId()).append(" , ")
					   .append("name : '").append(atividade.getNomeAtividade()).append("' , ")
					   .append("type : '").append(atividade.getType().getName()).append("'")
				   .append("}")
			.append(",");
		}
		builder = builder.deleteCharAt(builder.length()-1); 
		return builder.toString();
	}
}
