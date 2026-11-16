package com.Ventana;

import java.util.Optional;

public interface UsuarioRepositorio {
    public Optional<Usuario> buscarUsuario(String nombreUsuario){};
    public List<Usuario> lista(){};
    public Usuario guardar(Usuario usuario){};
}
