import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorizingCardComponent } from './memorizing-card.component';

describe('MemorizingCardComponent', () => {
  let component: MemorizingCardComponent;
  let fixture: ComponentFixture<MemorizingCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorizingCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorizingCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
