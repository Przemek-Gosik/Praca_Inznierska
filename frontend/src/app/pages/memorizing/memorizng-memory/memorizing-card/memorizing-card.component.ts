import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CardDetails } from 'src/app/models/memorizing-model';
import { ThemeService } from 'src/app/services/theme.service';


@Component({
  selector: 'app-memorizing-card',
  templateUrl: './memorizing-card.component.html',
  styleUrls: ['./memorizing-card.component.css'],
  animations: [
    trigger('cardChosen',[
      state('none',style({
        transform:'none',
      })),
      state('chosen',style({
        transform: 'perspective(600px) rotateY(180deg)'
      })),
      state('matched',style({
        opacity: 0.3
      })),
      transition('none => chosen',[
        animate('400ms')
      ]),
      transition('chosen => none',[
        animate('400ms')
      ]),
      transition('chosen => matched',[
        animate('400ms')
      ])
    ])
  ]
})
export class MemorizingCardComponent implements OnInit {

  @Input() data: CardDetails = {
    imageName: '',
    state: 'none'
  };

  @Output() cardClicked = new EventEmitter();

  constructor(protected themeService: ThemeService) { }

  ngOnInit(): void {
  }

 

}
