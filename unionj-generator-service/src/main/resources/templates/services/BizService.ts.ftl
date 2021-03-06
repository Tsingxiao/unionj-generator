/**
* Generated by unionj-generator.
* You can edit it as your need.
*/
export class BizService {

  static serverName: string | undefined = "${serverName!}";

  axios: any;

  constructor(axios: any) {
    this.axios = axios;
  }

  protected addPrefix(endpoint: string){
    if(BizService.serverName) {
      if(BizService.serverName.indexOf("/") === 0 || BizService.serverName.indexOf("http") === 0) {
        return BizService.serverName + endpoint
      }
      return "/" + BizService.serverName + endpoint
    }
    return endpoint
  }

}

export default BizService;
