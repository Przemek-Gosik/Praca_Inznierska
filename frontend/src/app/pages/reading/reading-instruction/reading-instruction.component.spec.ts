import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadingInstructionComponent } from './reading-instruction.component';

describe('ReadingInstructionComponent', () => {
  let component: ReadingInstructionComponent;
  let fixture: ComponentFixture<ReadingInstructionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReadingInstructionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReadingInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
