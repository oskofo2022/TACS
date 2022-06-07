import { UnauthorizedException } from "./UnauthorizedException";

class RequestError extends Error {
    response: Response;
  
    constructor(message: string, response: Response) {
      super(message);
      this.response = response;
      this.message = message;
    }
  }
  
  const StatusCodeHandler = (response: Response) => {
    if (response.ok) return response;
    if(response.status === 401) throw new UnauthorizedException('Unauthorized');
    const error = new RequestError(response.status.toString(), response);
    throw error;
  };
  
  export default StatusCodeHandler;