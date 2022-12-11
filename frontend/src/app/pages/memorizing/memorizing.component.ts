import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-memorizing',
  templateUrl: './memorizing.component.html',
  styleUrls: ['./memorizing.component.css']
})
export class MemorizingComponent implements OnInit {
  title: string = "Szybkie zapamiętywanie";
  constructor() { }

  ngOnInit(): void {
  }

}
