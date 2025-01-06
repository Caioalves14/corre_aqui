import React, {useState, useRef} from 'react'
import api from '../services/api'
import './Home.css'
import supabase from '../supabase';
import Logo from "../assets/Logo.jpeg"

const Home = () => {
  const [ofertas, setOfertas] = useState([]);

  const inputNomeProduto = useRef();
  const inputPreco = useRef();
  const inputImagem = useRef();

  async function uploadImagem(file) {
    try {
      const fileName = `${Date.now()}_${file.name}`;

      const {data, error} = await supabase.storage
      .from('uploads')
      .upload(`produtos/${fileName}`, file);

      if(error){
        throw error;
      }

      const { data: publicUrlData, error: PublicUrlError } = supabase.storage
      .from('uploads')
      .getPublicUrl(`produtos/${fileName}`);

      if(PublicUrlError){
        throw PublicUrlError;
      }

      return publicUrlData.publicUrl;
    }catch(error) {
      console.error("Error ao fazer o upload da imagem!", error.message);
      throw error;
    }
  }
 
  async function cadastrarOferta(e) {
    e.preventDefault();

    try {
      const file = inputImagem.current.files[0];
      if (!file){
        alert("Por favor, selecione uma imagem para o produto!");
        return;
      }

      const imagemUrl = await uploadImagem(file);

      await api.post('/oferta/criar', {
        nomeProduto: inputNomeProduto.current.value,
        preco : inputPreco.current.value,
        imagem: imagemUrl,
      });

      alert("Oferta cadastrada com sucesso!");

      inputNomeProduto.current.value = "";
      inputPreco.current.value = "";
      inputImagem.current.value = "";

    } catch (error) {
      console.error("Erro ao cadastrar oferta:", error.message);
      alert("Erro ao cadastrar a oferta tente novamente")

    }

  }

  async function buscarOfertas() {
    try {
      const response = await api.get('/ofertas');
      setOfertas(response.data);
    }catch (error){
      console.error("Erro ao buscar ofertas:", error.message)
    }
  }

  React.useEffect(() => {
    buscarOfertas();
  }, []);

  return (
      <form onSubmit={cadastrarOferta}>
        <div className='main-ofertas'>
          <div className='left-ofertas'>
            {ofertas.map(oferta => (
              <div className='ofertas' key={oferta.id}>
              <div className='oferta'>
                <p>Nome:<span> {oferta.nome}</span></p>
                <p>Preço: {oferta.preco}</p>
                <div className='imagem'>
                  <p>Imagem:</p>
                  <img src={oferta.imagem || Logo} alt="imagem-produto" />
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
                <button type='submit' className="btn-cadastro">Cadastrar oferta</button>
              </div>
          </div>
        </div>
        </form>
    )
      
}

export default Home