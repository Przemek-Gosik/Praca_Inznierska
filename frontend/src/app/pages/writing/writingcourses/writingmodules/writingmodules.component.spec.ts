import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingmodulesComponent } from './writingmodules.component';

describe('WritingmodulesComponent', () => {
  let component: WritingmodulesComponent;
  let fixture: ComponentFixture<WritingmodulesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingmodulesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingmodulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
