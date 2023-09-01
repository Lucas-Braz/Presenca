package com.MundoSenai.Presenca.Controller;

import com.MundoSenai.Presenca.Model.M_Pessoa;
import com.MundoSenai.Presenca.Model.M_Resposta;
import com.MundoSenai.Presenca.Service.S_Pessoa;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("usuario")
public class C_Pessoa {
    @GetMapping("/")
    public String helloWorld(){
        return "Login/login";
    }

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario,
                            @RequestParam("senha") String senha,
                            HttpSession session,
                            RedirectAttributes redirectAttributes){
        M_Pessoa pessoa = S_Pessoa.getPessoaLogin(usuario,senha);
        session.setAttribute("usuario", pessoa);
        if(session.getAttribute("usuario") == null){
            return "Login/login";
        }else{
            redirectAttributes.addFlashAttribute("nome",pessoa.getNome());
            return "redirect:/Home";
        }
    }

    @ModelAttribute("usuario")
    public M_Pessoa getUsuario(HttpSession session) {
        return (M_Pessoa) session.getAttribute("usuario");
    }

    @GetMapping("/Home")
    public String getHome(@ModelAttribute("usuario") String usuario){
        if (usuario != null) {
            // A sessão existe, redirecionar para a página home
            return "Home/home";
        } else {
            // A sessão não existe, redirecionar para a página de login
            return "redirect:/";
        }
    }

    @GetMapping("/cadastro")
    public String getCadastro(){
        return "Pessoa/cadastro";
    }

    @PostMapping("/cadastro")
    @ResponseBody
    public M_Resposta postCadastro(@RequestParam("nome") String nome,
                                     @RequestParam("email") String email,
                                     @RequestParam("cpf") String cpf,
                                     @RequestParam("telefone") String telefone,
                                     @RequestParam("data_nasc") String data_nascimento,
                                     @RequestParam("senha") String senha,
                                     @RequestParam("confsenha") String conf_senha) {
        return S_Pessoa.cadastrarPessoa(nome, email, cpf, telefone, data_nascimento, senha, conf_senha);
    }
}