import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import PersonForm from './PersonForm';

// Mock des personService
jest.mock('../services/personService', () => ({
  personService: {
    create: jest.fn(),
    update: jest.fn(),
  },
}));

describe('PersonForm Component', () => {
  
  describe('Rendering', () => {
    test('sollte alle Formularfelder rendern', () => {
      render(<PersonForm />);
      
      expect(document.getElementById('name')).toBeInTheDocument();
      expect(document.getElementById('vorname')).toBeInTheDocument();
      expect(document.getElementById('thema')).toBeInTheDocument();
      expect(document.getElementById('abgabedatum')).toBeInTheDocument();
    });
    
    test('sollte "Neue Person erstellen" Titel zeigen', () => {
      render(<PersonForm />);
      
      expect(screen.getByText(/neue person erstellen/i)).toBeInTheDocument();
    });
    
    test('sollte Erstellen Button zeigen', () => {
      render(<PersonForm />);
      
      expect(screen.getByRole('button', { name: /erstellen/i })).toBeInTheDocument();
    });
    
    test('sollte "Person bearbeiten" Titel zeigen wenn existingPerson', () => {
      const existingPerson = {
        id: 1,
        name: 'Test',
        vorname: 'User',
        thema: 'Thema',
        abgabedatum: '2026-06-15',
      };
      
      render(<PersonForm existingPerson={existingPerson} />);
      
      expect(screen.getByText(/person bearbeiten/i)).toBeInTheDocument();
    });
    
    test('sollte Formularfelder mit Daten vorausfüllen', () => {
      const existingPerson = {
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Test Thema',
        abgabedatum: '2026-06-15',
      };
      
      render(<PersonForm existingPerson={existingPerson} />);
      
      expect(screen.getByDisplayValue('Mustafa')).toBeInTheDocument();
      expect(screen.getByDisplayValue('Sagaaro')).toBeInTheDocument();
    });
  });
  
  describe('Form Interactions', () => {
    test('sollte Eingaben aktualisieren', async () => {
      render(<PersonForm />);
      
      const nameInput = document.getElementById('name');
      await userEvent.type(nameInput, 'TestName');
      
      expect(nameInput.value).toBe('TestName');
    });
    
    test('sollte Vorname-Eingabe aktualisieren', async () => {
      render(<PersonForm />);
      
      const vornameInput = document.getElementById('vorname');
      await userEvent.type(vornameInput, 'TestVorname');
      
      expect(vornameInput.value).toBe('TestVorname');
    });
    
    test('sollte Thema-Eingabe aktualisieren', async () => {
      render(<PersonForm />);
      
      const themaInput = document.getElementById('thema');
      await userEvent.type(themaInput, 'TestThema');
      
      expect(themaInput.value).toBe('TestThema');
    });
  });
});
