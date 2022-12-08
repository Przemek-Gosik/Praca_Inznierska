import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MenuComponent } from './pages/menu/menu.component';
import { SigninComponent } from './pages/signin/signin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { MatIconModule, MatToolbarModule, MatMenuModule, MatSelectModule, MatCardModule } from '@angular/material';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { ReadingComponent } from './pages/reading/reading.component';
import { WritingComponent } from './pages/writing/writing.component';
import { MemorizingComponent } from './pages/memorizing/memorizing.component';
import { AccountComponent } from './pages/account/account.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { ContactComponent } from './pages/contact/contact.component';
import { FooterComponent } from './pages/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent,
    SigninComponent,
    SignupComponent,
    PageNotFoundComponent,
    ReadingComponent,
    WritingComponent,
    MemorizingComponent,
    AccountComponent,
    SettingsComponent,
    ContactComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatSelectModule,
    MatCardModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
