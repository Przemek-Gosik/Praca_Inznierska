import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-writing',
  templateUrl: './writing.component.html',
  styleUrls: ['./writing.component.css']
})
export class WritingComponent implements OnInit {
  title: string = "Szybkie pisanie";
  constructor() { }

  ngOnInit(): void {
  }

}
