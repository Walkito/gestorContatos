import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PessoasComponent } from './pessoas.component';

describe('PessoasComponent', () => {
  let component: PessoasComponent;
  let fixture: ComponentFixture<PessoasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PessoasComponent]
    });
    fixture = TestBed.createComponent(PessoasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
