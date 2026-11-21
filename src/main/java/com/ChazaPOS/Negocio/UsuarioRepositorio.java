package com.ChazaPOS.Negocio;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio {
    Optional<Usuario> buscarUsuario(String nombreUsuario);
    List<Usuario> lista();
    Usuario guardar(Usuario usuario);
}
