type Content = {
  id: string;
  headline: string;
  plainText: string;
  imageUrl?: string;
};

class ContentRepository {
  private contents: Content[] = [
    {
      id: "1cc4166d-45dc-4e2a-b3f2-904dc68f360c",
      headline: "Understanding the Basics of TypeScript",
      imageUrl: "https://picsum.photos/800/400?random=1",
      plainText: "TypeScript is a typed superset of JavaScript that compiles to plain JavaScript. It offers static typing, classes, and interfaces, making it easier to build large-scale applications. In this article, we will explore the fundamental concepts of TypeScript and how it can enhance your development workflow.",
    },
    {
      id: "2bb5277e-56ed-5f3b-c4g3-015ed79g471d",
      headline: "Getting Started with React Native Development",
      imageUrl: "https://picsum.photos/800/400?random=2",
      plainText: "React Native allows you to build mobile applications using JavaScript and React. With React Native, you can create truly native apps that run on both iOS and Android platforms. Learn the fundamentals of mobile development with React Native and start building your first app today.",
    },
    {
      id: "3cc6388f-67fe-6g4c-d5h4-126fe80h582e",
      headline: "Modern JavaScript ES6+ Features You Should Know",
      imageUrl: "https://picsum.photos/800/400?random=3",
      plainText: "JavaScript has evolved significantly with ES6 and beyond, introducing powerful features like arrow functions, destructuring, promises, and async/await. These modern syntax improvements make your code more readable and maintainable. Discover the essential ES6+ features that every JavaScript developer should master.",
    },
  ];
  
  getContentById(id: string): Content | undefined {
    return this.contents.findLast(content => content.id === id);
  }

  getAllContents(): Content[] {
    return this.contents;
  }
}

export const contentRepository = new ContentRepository();
