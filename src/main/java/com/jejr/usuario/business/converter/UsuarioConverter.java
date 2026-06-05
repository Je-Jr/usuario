package com.jejr.usuario.business.converter;

import com.jejr.usuario.business.dto.EnderecoDTO;
import com.jejr.usuario.business.dto.TelefoneDTO;
import com.jejr.usuario.business.dto.UsuarioDTO;
import com.jejr.usuario.infrastructure.entity.Endereco;
import com.jejr.usuario.infrastructure.entity.Telefone;
import com.jejr.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
  public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
    return Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .endereco(paraListaEndereco(usuarioDTO.getEndereco()))
            .telefone(paraListaTelefones(usuarioDTO.getTelefone()))
            .build();
  }

  public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO) {
    return enderecoDTO.stream().map(this::paraEndereco).toList();
  }

  public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
    return Endereco.builder()
            .rua(enderecoDTO.getRua())
            .numero(enderecoDTO.getNumero())
            .cidade(enderecoDTO.getCidade())
            .complemento(enderecoDTO.getComplemento())
            .cep(enderecoDTO.getCep())
            .estado(enderecoDTO.getEstado())
            .build();
  }

  public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTO) {
    return telefoneDTO.stream().map(this::paraTelefone).toList();
  }

  public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
    return Telefone.builder()
            .numero(telefoneDTO.getNumero())
            .ddd(telefoneDTO.getDdd())
            .build();
  }







  public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
    return UsuarioDTO.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .endereco(paraListaEnderecoDTO(usuarioDTO.getEndereco()))
            .telefone(paraListaTelefonesDTO(usuarioDTO.getTelefone()))
            .build();
  }

  public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco) {
    return endereco.stream().map(this::paraEndereco).toList();
  }

  public EnderecoDTO paraEndereco(Endereco enderecoDTO) {
    return EnderecoDTO.builder()
            .rua(enderecoDTO.getRua())
            .numero(enderecoDTO.getNumero())
            .cidade(enderecoDTO.getCidade())
            .complemento(enderecoDTO.getComplemento())
            .cep(enderecoDTO.getCep())
            .estado(enderecoDTO.getEstado())
            .build();
  }

  public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTO) {
    return telefoneDTO.stream().map(this::paraTelefoneDTO).toList();
  }

  public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
    return TelefoneDTO.builder()
            .numero(telefoneDTO.getNumero())
            .ddd(telefoneDTO.getDdd())
            .build();
  }
}
