package br.com.fiap.BloomCare.dto;

import br.com.fiap.BloomCare.entities.Nutriz;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NutrizDto {
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    private String email;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    @NotBlank(message = "O campo telefone é obrigatório. Ex:(x)xxxxx-xxxx")
    private String telefone;

    @NotNull(message = "Campo data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser anterior à data atual.")
    private LocalDate dtNascimento;

    @NotBlank(message = "O campo cpf é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    private LocalDate dtCadastro;

    //construtor de nutrizes
    public NutrizDto(Nutriz nutriz){
        id= nutriz.getId();
        nome = nutriz.getNome();
        email = nutriz.getEmail();
        senha = nutriz.getSenha();
        telefone = nutriz.getTelefone();
        dtNascimento = nutriz.getDtNascimento();
        cpf = nutriz.getCpf();
        dtCadastro = nutriz.getDtCadastro();
    }

    // Método estático para conversão inversa
    public Nutriz toEntity() {
        Nutriz nutriz = new Nutriz();
        nutriz.setId(id);
        nutriz.setNome(nome);
        nutriz.setEmail(email);
        nutriz.setSenha(senha);
        nutriz.setTelefone(telefone);
        nutriz.setDtNascimento(dtNascimento);
        nutriz.setCpf(cpf);
        nutriz.setDtCadastro(LocalDate.now());
        return nutriz;
    }
}
