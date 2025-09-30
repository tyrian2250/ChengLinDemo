

const tsPlugin = require('@typescript-eslint/eslint-plugin');
const tsParser = require('@typescript-eslint/parser');

module.exports = [
  {
    files: ['**/*.{ts,tsx}'],
    languageOptions: {
      parser: tsParser,
      parserOptions: {
        project: './html/myApp/tsconfig.json',
        sourceType: 'module',
      },
    },
    plugins: {
      '@typescript-eslint': tsPlugin,
    },
    rules: {
      ...tsPlugin.configs.recommended.rules,
      semi: 'error',
      quotes: ['error', 'single']
    },
  },
  {
    files: ['**/*.{js,jsx}'],
    rules: {
      semi: 'error',
      quotes: ['error', 'single']
    },
  },
];
