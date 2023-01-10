import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TypeReading } from 'src/app/consts/type-consts';

@Component({
  selector: 'app-reading',
  templateUrl: './reading.component.html',
  styleUrls: ['./reading.component.css']
})
export class ReadingComponent implements OnInit {
  title: string = "Szybkie czytanie";
  SCHULTZ: string = TypeReading.SCHULTZ
  FINDING_NUMBERS: string = TypeReading.FINDING_NUMBERS
  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  tabSchultz: string = "Tablice Schulza"
  findNumbers: string = "Znajdowanie liczb"

  linkToLevelChoice(type:string){
    this.router.navigate(["/courses/reading/level",
    {
      type: type
    }])
  }

}
