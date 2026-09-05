package com.jejr.usuario.business;

import com.jejr.usuario.business.converter.UsuarioConverter;
import com.jejr.usuario.business.dto.EnderecoDTO;
import com.jejr.usuario.business.dto.TelefoneDTO;
import com.jejr.usuario.business.dto.UsuarioDTO;
import com.jejr.usuario.infrastructure.entity.Endereco;
import com.jejr.usuario.infrastructure.entity.Telefone;
import com.jejr.usuario.infrastructure.entity.Usuario;
import com.jejr.usuario.infrastructure.exceptions.ConflictException;
import com.jejr.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.jejr.usuario.infrastructure.repository.EnderecoRepository;
import com.jejr.usuario.infrastructure.repository.TelefoneRepository;
import com.jejr.usuario.infrastructure.repository.UsuarioRepository;
import com.jejr.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioConverter usuarioConverter;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final EnderecoRepository enderecoRepository;
  private final TelefoneRepository telefoneRepository;

  public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
    emailExiste(usuarioDTO.getEmail());
    usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
    Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
  }

  public void emailExiste(String email) {
    try {
      boolean existe = verificaEmailExistente(email);
      if(existe){
        throw new ConflictException("Usuário já cadastrado" + email);
      }
    }
    catch (ConflictException e) {
      throw new ConflictException("Email já cadastrado", e.getCause());
    }
  }

  public boolean verificaEmailExistente(String email) {
    return usuarioRepository.existsByEmail(email);
  }

  public UsuarioDTO buscarUsuarioPorEmail(String email) {
    try {
      return usuarioConverter.paraUsuarioDTO(
              usuarioRepository.findByEmail(email)
                      .orElseThrow(
                              () -> new ResourceNotFoundException("Email não encontrado " + email)));
    } catch (ResourceNotFoundException e) {
      throw new ResourceNotFoundException("Email não encontrado " + email);
    }
  }

  public void deletarUsuarioPorEmail(String email) {
    usuarioRepository.deleteByEmail(email);
  }

  public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
    String email = jwtUtil.extrairEmailToken(token.substring(7));

    //Criptografia de senha
    dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

    Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
            new ResourceNotFoundException("Email não localizado"));

    Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

    return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
  }

  public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

    Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
            new ResourceNotFoundException("Id não encontrado " + idEndereco));

    Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

    return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
  }

  public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

    Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
            new ResourceNotFoundException("Id não encontrado " + idTelefone));

    Telefone telefone = usuarioConverter.updaeTelefone(telefoneDTO, entity);

    return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
  }

  public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
    String email = jwtUtil.extrairEmailToken(token.substring(7));
    Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
            new ResourceNotFoundException("Email não localizado " + email));

    Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
    Endereco enderecoEntity = enderecoRepository.save(endereco);
    return usuarioConverter.paraEnderecoDTO(enderecoEntity);
  }

  public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
    String email = jwtUtil.extrairEmailToken(token.substring(7));
    Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
            new ResourceNotFoundException("Email não localizado " + email));

    Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
    Telefone telefoneEntity = telefoneRepository.save(telefone);
    return usuarioConverter.paraTelefoneDTO(telefoneEntity);
  }

}
