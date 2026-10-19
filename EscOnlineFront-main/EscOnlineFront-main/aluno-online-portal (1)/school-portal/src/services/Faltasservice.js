export async function buscarFrequencia(mesIndex) {
  await new Promise((resolve) => setTimeout(resolve, 500));
  return [
    { subject: 'Aprofundamento em Matemática', absences: 0 },
    { subject: 'Biologia', absences: 2 },
    { subject: 'Educação Física', absences: 0 },
    { subject: 'Estágio Curricular', absences: 0 },
    { subject: 'Filosofia', absences: 1 },
    { subject: 'Física', absences: 0 },
    { subject: 'Geografia', absences: 0 },
    { subject: 'História', absences: 0 },
    { subject: 'Horário de Estudo I', absences: 0, dividerAfter: true },
    { subject: 'Horário de Estudo II', absences: 3 },
    { subject: 'Língua Estrangeira - Espanhol', absences: 0 },
    { subject: 'Língua Estrangeira - Inglês', absences: 0 },
    { subject: 'Língua Portuguesa', absences: 0 },
  ];
}
