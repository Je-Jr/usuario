package com.jejr.usuario.business;

import com.jejr.usuario.business.converter.UsuarioConverter;
import com.jejr.usuario.business.dto.UsuarioDTO;
import com.jejr.usuario.infrastructure.entity.Usuario;
import com.jejr.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioConverter usuarioConverter;

  public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
    Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
  }
}
