import { router } from "expo-router";
import { Image, Pressable, ScrollView, Text, View } from "react-native";
import { contentRepository } from "./content/content";
import { styles } from "./styles/index.styles";

export default function Index() {
  const contents = contentRepository.getAllContents();

  const handleArticlePress = (contentId: string, assignedGroup?: string) => {
    router.push({
      pathname: "/article/article",
      params: { id: contentId, assignedGroup: assignedGroup || "" },
    });
  };

  return (
    <ScrollView style={styles.container}>
      {contents.map((content, index) => (
        <View key={content.id + index}>
          <Pressable 
            style={styles.card}
            onPress={() => handleArticlePress(content.id, index === 0 ? "control" : index === 1 ? "variant" : undefined)}
          >
            <View style={styles.cardContent}>
              {content.imageUrl && (
                <Image
                  source={{ uri: content.imageUrl }}
                  style={styles.articleImage}
                  resizeMode="cover"
                />
              )}
              <Text style={styles.headline} numberOfLines={2}>
                {content.headline}
              </Text>
              <Text style={styles.plainText} numberOfLines={2}>
                {content.plainText}
              </Text>
              <Text style={styles.readMore}>Read more →</Text>
            </View>
          </Pressable>
          {index < contents.length - 1 && <View style={styles.divider} />}
        </View>
      ))}
    </ScrollView>
  );
}
