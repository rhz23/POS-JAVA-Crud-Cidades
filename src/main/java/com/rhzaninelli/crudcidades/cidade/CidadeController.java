package com.rhzaninelli.crudcidades.cidade;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;

    private final CidadeRepository cidadeRepository;

    public CidadeController(CidadeRepository cidadeRepository){
        cidades = new HashSet<>();
        this.cidadeRepository = cidadeRepository;
    }

    @GetMapping("/")
    public String listar(Model memoria, Principal usuario, HttpSession sessao, HttpServletResponse response) {

        response.addCookie(new Cookie("listar", LocalDateTime.now().toString()));

        memoria.addAttribute("listaCidades", cidadeRepository
                        .findAll()
                        .stream()
                        .map(cidade -> new Cidade(cidade.getNome(), cidade.getEstado()))
                        .collect(Collectors.toList())
                );

        sessao.setAttribute("usuarioAtual", usuario.getName());

        return "/crud";
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria, HttpServletResponse response){

        response.addCookie(new Cookie("criar", LocalDateTime.now().toString()));

        if (validacao.hasErrors()){
            validacao
                    .getFieldErrors()
                    .forEach(error -> memoria.addAttribute(
                            error.getField(),
                            error.getDefaultMessage())
                    );
            memoria.addAttribute("nomeInformado", cidade.getNome());
            memoria.addAttribute("estadoInformado", cidade.getEstado());
            memoria.addAttribute("listaCidades", cidades);
            return ("/crud");
        } else {
            cidadeRepository.save(cidade.clonar());
        }

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam String nome, @RequestParam String estado, HttpServletResponse response){

        response.addCookie(new Cookie("excluir", LocalDateTime.now().toString()));

        var cidadeEstadoEncontrada = cidadeRepository.findByNomeAndEstado(nome, estado);

        cidadeEstadoEncontrada.ifPresent(cidadeRepository::delete);

        return "redirect:/";
    }

    @GetMapping("/preparaAlterar")
    public String preparaAlterar(@RequestParam String nome, @RequestParam String estado, Model memoria, HttpServletResponse response){

        response.addCookie(new Cookie("alterar", LocalDateTime.now().toString()));

        var cidadeAtual = cidadeRepository.findByNomeAndEstado(nome, estado);

        cidadeAtual.ifPresent(cidadeEncontrada -> {
            memoria.addAttribute("cidadeAtual", cidadeEncontrada);
            memoria.addAttribute("listaCidades", cidadeRepository.findAll());
        });

        return "/crud";
    }

    @PostMapping("/alterar")
    public String alterar(@RequestParam String nomeAtual, @RequestParam String estadoAtual, Cidade cidade){

        var cidadeAtual = cidadeRepository.findByNomeAndEstado(nomeAtual, estadoAtual);

        if (cidadeAtual.isPresent()){
            var cidadeEncontrada = cidadeAtual.get();
            cidadeEncontrada.setNome(cidade.getNome());
            cidadeEncontrada.setEstado(cidade.getEstado());

            cidadeRepository.saveAndFlush(cidadeEncontrada);
        }

        return "redirect:/";
    }

    @GetMapping("/mostrar")
    @ResponseBody
    public String mostraCookieAlterar(@CookieValue String listar) {
        return "Ultimo acesso ao método listar(): " + listar;
    }
    

}
