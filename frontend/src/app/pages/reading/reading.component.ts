import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TypeReading } from 'src/app/consts/type-consts';
import { ReadingInstructionComponent } from './reading-instruction/reading-instruction.component';

@Component({
  selector: 'app-reading',
  templateUrl: './reading.component.html',
  styleUrls: ['./reading.component.css']
})
export class ReadingComponent implements OnInit {
  title: string = "Szybkie czytanie";
  SCHULTZ: string = TypeReading.SCHULTZ
  FINDING_NUMBERS: string = TypeReading.FINDING_NUMBERS
  QUIZ :string = TypeReading.READING_WITH_QUIZ
  EYE_WARM_UP:string = "EYE_WARM_UP"; 

  constructor(private router:Router,
    public dialog: MatDialog,) { }

  ngOnInit(): void {
  }

  tabSchultz: string = "Tablice Schulza"
  findNumbers: string = "Znajdowanie liczb"
  eyeTraining: string = "Rozgrzewka oczu"
  quiz: string = "Quiz"

  linkToLevelChoice(type:string){
    this.router.navigate(["/courses/reading/level",
    {
      type: type
    }])
  }

  openDialog(type:string): void {
    this.dialog.open(ReadingInstructionComponent, {
      width:'800px',
      data:{
        type:type
      }
    });
  }

}
