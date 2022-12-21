import { Component, OnInit } from '@angular/core';
import { LevelSelect } from 'src/app/models/level-model';
import { LevelConsts } from 'src/app/consts/level-consts';
@Component({
  selector: 'app-writingtexts',
  templateUrl: './writingtexts.component.html',
  styleUrls: ['./writingtexts.component.css']
})
export class WritingtextsComponent implements OnInit {

  levels : LevelSelect[] = LevelConsts.LEVEL_SELECT 
  chosenLevelName: string = ""
  favoriteSeason: string = "";
  seasons: string[] = ['Winter', 'Spring', 'Summer', 'Autumn'];
  constructor() { }

  ngOnInit(): void {
  }

}
