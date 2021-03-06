import { Injectable } from '@angular/core';

declare var Keycloak: any;

@Injectable()
export class KeycloakService {
  static auth: any = {};
	
  static init(): Promise<any> {
    let keycloakAuth: any = new Keycloak('keycloak.json');
    console.log(keycloakAuth);
    KeycloakService.auth.loggedIn = false;

      return new Promise((resolve, reject) => {
        keycloakAuth.init({ onLoad: 'login-required' })
          .success(() => {
            KeycloakService.auth.loggedIn = true;
            KeycloakService.auth.authz = keycloakAuth;
            KeycloakService.auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/demo/protocol/openid-connect/logout?redirect_uri=/index.html";
            resolve();
          })
          .error(() => {
            reject();
          });
      });
    }

  logout() {
    console.log('*** LOGOUT');
    KeycloakService.auth.loggedIn = false;
	KeycloakService.auth.authz.logout();
	KeycloakService.auth.authz = null;
    //window.location.href = KeycloakService.auth.logoutUrl;
  }
  
  getUserProfile(): Promise<any> {
  	return new Promise<any>((resolve, reject) => {
  		KeycloakService.auth.authz.loadUserProfile().success((data) => {
  			console.log(data);
  			resolve(<any>data);
  		}).error(() => {
  		});
  	});
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz.token) {
        KeycloakService.auth.authz.updateToken(5)
          .success(() => {
            resolve(<string>KeycloakService.auth.authz.token);
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      }
    });
  }
}