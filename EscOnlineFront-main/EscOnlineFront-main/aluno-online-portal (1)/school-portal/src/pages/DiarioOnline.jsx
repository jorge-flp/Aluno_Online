import { useEffect, useState } from "react";
import { listarFaltas } from "../services/faltasService";

export default function DiarioOnline() {
  const [faltas, setFaltas] = useState([]);

  const [carregando, setCarregando] = useState(true);

  useEffect(() => {
    async function carregarFaltas() {
      const alunoIdFake = 1;
      const dados = await listarFaltas(alunoIdFake);
      setFaltas(dados);
      setCarregando(false);
    }

    carregarFaltas();
  }, []);

  if (carregando) {
    return <p>Carregando faltas...</p>;
  }

  return (
    <div>
      <h1>Diário Online - Faltas</h1>
      <ul>
        {faltas.map((falta) => (
          <li key={falta.id}>
            {falta.data} - {falta.disciplina}
          </li>
        ))}
      </ul>
    </div>
  );
}