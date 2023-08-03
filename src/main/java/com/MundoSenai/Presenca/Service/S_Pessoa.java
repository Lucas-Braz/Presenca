package com.MundoSenai.Presenca.Service;

import com.MundoSenai.Presenca.Model.M_Pessoa;
import com.MundoSenai.Presenca.Repository.R_Pessoa;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class S_Pessoa {
    private static R_Pessoa r_pessoa;

    public S_Pessoa(R_Pessoa r_pessoa){
        this.r_pessoa = r_pessoa;
    }

    public static M_Pessoa getPessoaLogin(String usuario, String senha){
        return r_pessoa.findByUsuarioESenha(Long.valueOf(usuario),senha);
    }

    public static String cadastrarPessoa(String nome, String email, String cpf,
                                String telefone, String data_nascimento,
                                String senha, String conf_senha){
        if(!senha.equals(conf_senha)) {
            return "A Senha e a Confirmação de Senha devem serm iguais";
        }else if(!CpfValidator.validateCPF(cpf)){
            return "CPF Inválido";
        }else if(nome == null || nome.trim() == ""){
            return "Deve ser informado o Nome";
        }else if ((email == null || email.trim() == "")
                && (NumberCleaner.cleanNumber(telefone) == null
                || NumberCleaner.cleanNumber(telefone).trim() == "")){
            return "e-Mail ou Telefone precisa ser informado";
        }else {
            M_Pessoa m_pessoa = new M_Pessoa();
            m_pessoa.setNome(nome);
            m_pessoa.setCpf(Long.valueOf(cpf));
            m_pessoa.setTelefone(Long.valueOf(telefone));
            m_pessoa.setEmail(email);
            m_pessoa.setData_nasc(LocalDate.parse(data_nascimento));
            m_pessoa.setSenha(senha);
            r_pessoa.save(m_pessoa);
        }
        return "Cadastro efetuado com sucesso";
    }
}
