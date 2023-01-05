import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from 'src/app/services/localstorage.service';
// import {themeBtn, themeToggle, themeSelect} from "theme-change"

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  title: string = "Dlaczego warto?";
  title_reading: string = "Kurs szybkiego czytania";
  text_reading: string = " nie tylko pozwala na zdobycie umiejętności szybkiego zapoznawania się z tekstem, ale także lepszego zapamiętania. Liczba czytanych słów na minutę może wzrosnąć nawet dziesięciokrotnie! Dodatkowo ten kurs pozwoli Ci pozbyć się złych nawych i błędów popełnianych podczas czytania."
  title_writing: string = "Trening szybkiego pisania";
  text_writing: string = " pozwala zwiększyć prędkość co najmniej trzykrotnie. Dzięki temu nie tylko skrócisz swój czas pracy, ale również zadbasz o swój kręgosłup! Bezwzrokowe pisanie uchroni Cię przed ciągłym machaniem głową, które skutkuje późniejszymi problemami z mięśniami karku.";
  title_memorizing: string = "Ćwiczenie swojego mózgu";
  text_memorizing: string = " wpłynie na Twoje samopoczucie i pewność siebie oraz pozwoli Ci osiągać nowe, coraz ambitniejsze cele!";
  button_text: string = "Wybierz kurs, zacznij już dziś!"
  storedTheme: any 

  setting: any;
  theme: any;
  themeString: string = ""
  
  constructor( private localStorageService: LocalstorageService ) { }

  ngOnInit(): void {
 
  }

}
