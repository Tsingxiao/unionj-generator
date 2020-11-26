/**
* Generated by unionj-generator.
*/
export default class BizService {

  static serverName: string | undefined = "${serverName!}";

  axios: any;

  constructor(axios: any) {
    this.axios = axios;
  }

  protected addPrefix(endpoint: string){
    if(BizService.serverName) {
      if(BizService.serverName.startsWith("/") || BizService.serverName.startsWith("http")) {
        return BizService.serverName + endpoint
      }
      return "/" + BizService.serverName + endpoint
    }
    return endpoint
  }

}
