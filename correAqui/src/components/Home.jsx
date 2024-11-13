import React, {useState, useRef} from 'react'
import api from '../services/api'
import './Home.css'
import Logo from "../assets/Logo.jpeg"
import {initializeApp } from 'firebase/app';
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage";

const Home = () => {
  const [ofertas, setOfertas] = useState([]);

  const firebaseConfig = {
    apiKey: "AIzaSyDsf3-8dBxDXM-N8lBT-z9O7-J5DArlDQc",
    authDomain: "uploadimage-aab89.firebaseapp.com",
    projectId: "uploadimage-aab89",
    storageBucket: "uploadimage-aab89.firebasestorage.app",
    messagingSenderId: "878945807811",
    appId: "1:878945807811:web:6941d7d02152cff9e3e176",
  };

  const app = initializeApp(firebaseConfig);
  const storage = getStorage(app);

  async function uploadImage(file) {
    const imageRef = ref(storage, `images/${file.name}`);
    await uploadBytes(imageRef, file);
    return getDownloadURL(imageRef);
  }

  const inputNomeProduto = useRef();
  const inputPreco = useRef();
  const inputImagem = useRef();

  async function cadastrarOferta() {
    try {
      const file = inputImagem.current.files[0];
      const imageURL = await uploadImage(file);


      await api.post('/oferta/criar', {
        nomeProduto: inputNomeProduto.current.value,
        preco : inputPreco.current.value,
        imagem: inputImagem.current.value,
      })
    } catch (erro) {
      console.error("Erro ao cadastrar oferta:", error);
    }

  }
  return (
      <form>
        <div className='main-ofertas'>
          <div className='left-ofertas'>
            {ofertas.map(oferta => (
              <div className='ofertas' key={oferta.id}>
              <div className='oferta'>
                <p>Nome:<span> {oferta.nome}</span></p>
                <p>Preço: {oferta.preco}</p>
                <div className='imagem'>
                  <p>Imagem:</p>
                  <img src={Logo} alt="imagem-produto" />
                </div>
              </div>
            </div>
            ))}
          </div>
          <div className='rigth-ofertas'>
              <div className='card-ofertas'>
                <h2>Cadastre sua oferta:</h2>
                <div className='textfild'>
                  <label htmlFor="usuario">Nome do Produto:</label>
                  <input type="name" id='nome' placeholder='Nome do Produto' ref={inputNomeProduto} required/>
                </div>
                <div className='textfild'>
                  <label htmlFor="usuario">Preço:</label>
                  <input type="price" id='preco' placeholder='Preço do Produto' ref={inputPreco} required/>
                </div>
                <div className='textfild'>
                  <label htmlFor="usuario">Faça o upload da imagem do seu produto:</label>
                  <input type="file" id='imagem' placeholder='Adicione a foto do seu produto' ref={inputImagem} required/>
                </div>
                <button className="btn-cadastro" onClick={cadastrarOferta}>Cadastrar oferta</button>
              </div>
          </div>
        </div>
        </form>
    )
      
}

export default Home