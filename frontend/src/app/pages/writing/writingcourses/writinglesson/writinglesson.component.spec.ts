import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritinglessonComponent } from './writinglesson.component';

describe('WritinglessonComponent', () => {
  let component: WritinglessonComponent;
  let fixture: ComponentFixture<WritinglessonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritinglessonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritinglessonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
