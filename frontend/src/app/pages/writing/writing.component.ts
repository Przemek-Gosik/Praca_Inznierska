import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-writing',
  templateUrl: './writing.component.html',
  styleUrls: ['./writing.component.css']
})
export class WritingComponent implements OnInit {
  title: string = "Szybkie pisanie";
  course: string = "Przejdź do kursów";
  text: string = "Przejdź do pisania tekstów";
  constructor() { }

  ngOnInit(): void {
  }

}
