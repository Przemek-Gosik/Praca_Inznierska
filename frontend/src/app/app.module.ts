import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MenuComponent } from './pages/menu/menu.component';
import { SigninComponent } from './pages/signin/signin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { ReadingComponent } from './pages/reading/reading.component';
import { WritingComponent } from './pages/writing/writing.component';
import { MemorizingComponent } from './pages/memorizing/memorizing.component';
import { AccountComponent } from './pages/account/account.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { ContactComponent } from './pages/contact/contact.component';
import { FooterComponent } from './pages/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input"
import { MatSelectModule } from '@angular/material/select';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule} from '@angular/material/radio'
import { MatProgressBarModule} from '@angular/material/progress-bar'
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTabsModule } from '@angular/material/tabs';
import { FormsModule } from "@angular/forms";
import { MatchPasswordDirective } from './directives/match-password.directive';
import { VerificationCodeComponent } from './pages/signup/verification-code/verification-code.component';
import { WritingcoursesComponent } from './pages/writing/writingcourses/writingcourses.component';
import { WritingtextsComponent } from './pages/writing/writingtexts/writingtexts.component';
import { WritingmodulesComponent } from './pages/writing/writingcourses/writingmodules/writingmodules.component';
import { WritinglessonComponent } from './pages/writing/writingcourses/writinglesson/writinglesson.component';
import { SplitPipe } from './pages/writing/pipe/splitpipe';
import { SplitWordsPipe } from './pages/writing/pipe/split-words-pipe';
import { WritingLessonResultDialogComponent } from './pages/writing/writingcourses/writinglesson/writing-lesson-result-dialog/writing-lesson-result-dialog.component';
import { EditAccountComponent } from './pages/account/edit-account/edit-account.component';
import { WritingTestComponent } from './pages/writing/writingtexts/writing-test/writing-test.component';
import { SchulzComponent } from './pages/reading/schulz/schulz.component';
import { WritingTextResultDialogComponent } from './pages/writing/writingtexts/writing-test/writing-text-result-dialog/writing-text-result-dialog.component';
import { UsersComponent } from './pages/admin/users/users.component';
import { ReportsComponent } from './pages/admin/reports/reports.component';
import { ReportDetailsDialogComponent } from './pages/admin/reports/report-details-dialog/report-details-dialog.component';
import { ChooseLevelReadingComponent } from './pages/reading/choose-level-reading/choose-level-reading.component';
import { ChooseLevelMemorizingComponent } from './pages/memorizing/choose-level-memorizing/choose-level-memorizing.component';
import { MemorizngMnemonicsComponent } from './pages/memorizing/memorizng-mnemonics/memorizng-mnemonics.component';

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
    FooterComponent,
    MatchPasswordDirective,
    VerificationCodeComponent,
    WritingcoursesComponent,
    WritingtextsComponent,
    WritingmodulesComponent,
    EditAccountComponent,
    WritinglessonComponent,
    SplitPipe,
    SplitWordsPipe,
    WritingLessonResultDialogComponent,
    WritingTestComponent,
    SchulzComponent,
    WritingTextResultDialogComponent,
    UsersComponent,
    ReportsComponent,
    ReportDetailsDialogComponent,
    ChooseLevelReadingComponent,
    ChooseLevelMemorizingComponent,
    MemorizngMnemonicsComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatSelectModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatTabsModule,
    FormsModule,
    HttpClientModule,
    MatExpansionModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    MatProgressBarModule
  ],
  providers: [
    SplitPipe,
    SplitWordsPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
