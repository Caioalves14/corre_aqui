import React, { useState, useRef} from 'react'
import Logo from "../assets/Logo.jpeg"
import api from '../services/api';
import './Cadastro.css'

const Cadastro = () => {
    const [usuario, setUsuario] = useState([])

        const inputNome = useRef()
        const inputCNPJ = useRef()
        const inputEmail = useRef()
        const inputSenha = useRef()

        async function cadastrarUsuario() {
            await api.post('/loja/cadastro', {
                nome: inputNome.current.value,
                cnpj: inputCNPJ.current.value,
                email: inputEmail.current.value,
                senha: inputSenha.current.value
            })
        }

  return (
    <form >
        <div className="main-cadastro">
            <div className="left-cadastro">
               <img src={Logo}/>
            </div>
            <div className="rigth-cadastro">
                <div className="card-cadastro">
                    <h2>Faça o cadastro da sua Empresa:</h2>
                    <div className='textfild'>
                        <label htmlFor="usuario">Nome</label>
                        <input type="text" id='nome' placeholder='Nome'ref={inputNome} required/>
                    </div>
                    <div className='textfild'>
                        <label htmlFor="usuario">CNPJ ou CPF</label>
                        <input type="number" id='id' placeholder='CNPJ ou CPF' ref={inputCNPJ} required/>
                    </div>
                    <div className="textfild">
                        <label htmlFor="usuario">Email</label>
                        <input type="email" id="email" placeholder="Email" ref={inputEmail} required/>
                    </div>
                    <div className="textfild">
                        <label htmlFor="usuario">Senha</label>
                        <input type="password" id="senha" placeholder="Senha" ref={inputSenha} required/>
                    </div>
                    <button className="btn-cadastro" onClick={cadastrarUsuario}>Cadastrar Usuário</button>
                </div>
            </div>
        </div>
    </form>
  )
}

export default Cadastro