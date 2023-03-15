import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingtextsComponent } from './writingtexts.component';

describe('WritingtextsComponent', () => {
  let component: WritingtextsComponent;
  let fixture: ComponentFixture<WritingtextsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingtextsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingtextsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
