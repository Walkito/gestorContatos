export interface IErro{
    headers: any; // Aqui você pode definir o tipo correto para headers, se necessário
    status: number;
    statusText: string;
    url: string;
    ok: boolean;
    error: {
      error: string;
      message: string;
      errors: {
        defaultMessage: string;
        field: string;
        // Adicione outras propriedades aqui, se necessário
      }[];
      // Adicione outras propriedades aqui, se necessário
    };

}