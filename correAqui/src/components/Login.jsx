import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Logo from "../assets/Logo.jpeg"
import "./Login.css"

const Login = () => {

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async(e) => {
        e.preventDefault();

        try {
            const response = await fetch('', {
              method: 'GET',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({ email, senha }),
            });
      
            if (response.ok) {
              const data = await response.json();
              alert('Login bem-sucedido!');
              // Aqui você pode redirecionar o usuário para outra página
              // ou armazenar o token de autenticação, como:
              // localStorage.setItem('token', data.token);
              navigate('/dashboards/src/components/TelaInicial.jsx');
            } else {
              const errorData = await response.json();
              setErrorMessage(errorData.message || 'Erro ao fazer login');
            }
          } catch (error) {
            setErrorMessage('Erro no servidor. Tente novamente mais tarde.');
          }
        };

  return (
    <form onSubmit={handleSubmit}>
        <div className="main-login">
            <div className="left-login">
               <img src={Logo}/>
            </div>
            <div className="rigth-login">
                <div className="card-login">
                    <h2>Faça login como empresa:</h2>
                    <div className="textfild">
                        <label htmlFor="usuario">Email</label>
                        <input type="text" id="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)} required/>
                    </div>
                    <div className="textfild">
                        <label htmlFor="usuario">Senha</label>
                        <input type="password" id="senha" placeholder="Senha" onChange={(e)=> setSenha(e.target.value)} required/>
                    </div>
                    <button className="btn-login" type="submit">Login</button>
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