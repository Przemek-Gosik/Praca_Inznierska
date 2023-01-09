import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchubertComponent } from './schulz.component';

describe('SchubertComponent', () => {
  let component: SchubertComponent;
  let fixture: ComponentFixture<SchubertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchubertComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchubertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
