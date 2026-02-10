import { useState } from 'react';
import { HomePage } from './pages/HomePage';
import { TestPage } from './pages/TestPage';

type AppPage = 'main' | 'test';

export default function App() {
  const [currentPage, setCurrentPage] = useState<AppPage>('main');

  if (currentPage === 'test') {
    return <TestPage onNavigateBack={() => setCurrentPage('main')} />;
  }

  return <HomePage onNavigateToTest={() => setCurrentPage('test')} />;
}
