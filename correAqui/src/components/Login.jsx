import React, { useRef } from 'react'
import { useState } from 'react';
import Logo from "../assets/Logo.jpeg"
import "./Login.css"
import api from '../services/api';

const Login = () => {
  const inputEmail = useRef();
  const inputSenha = useRef();

  async function realizarLogin(event) {
    event.preventDefault();

    await api.get('/loja/login', {
      email: inputEmail.current.value,
      senha: inputSenha.current.value
    });

    const {token} = response.data;
    localStorage.setItem("authToken", token);

    alert("Login realizado com sucesso!")

    window.location.href = "/home";
  }
    

  return (
    <form>
        <div className="main-login">
            <div className="left-login">
               <img src={Logo}/>
            </div>
            <div className="rigth-login">
                <div className="card-login">
                    <h2>Faça login como empresa:</h2>
                    <div className="textfild">
                        <label htmlFor="usuario">Email</label>
                        <input type="text" id="email" placeholder="Email" ref={inputEmail} required/>
                    </div>
                    <div className="textfild">
                        <label htmlFor="usuario">Senha</label>
                        <input type="password" id="senha" placeholder="Senha" ref={inputSenha} required/>
                    </div>
                    <button className="btn-login" onClick={realizarLogin}>Login</button>
                    <a className="cadastro" href="./Cadastro.jsx">
                        <p className="complement">Não possui cadastro?</p>
                    </a>
                    <a className="recuperar-senha" href="#">
                        <p className="complement">Esqueci minha senha</p>
                    </a>
                </div>
            </div>
        </div>
    </form>
  )
}

export default Login