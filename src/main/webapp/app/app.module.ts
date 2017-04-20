import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { KeycloakService } from './keycloak.service';
import { AppComponent } from './app.component';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [
    KeycloakService,
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {}