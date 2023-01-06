import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reading',
  templateUrl: './reading.component.html',
  styleUrls: ['./reading.component.css']
})
export class ReadingComponent implements OnInit {
  title: string = "Szybkie czytanie";
  constructor() { }

  ngOnInit(): void {
  }

  tabSchultz: string = "Tablice Schulza"

}
